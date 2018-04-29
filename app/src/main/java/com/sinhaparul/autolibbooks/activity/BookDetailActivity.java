package com.sinhaparul.autolibbooks.activity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.sinhaparul.autolibbooks.Constants;
import com.sinhaparul.autolibbooks.R;
import com.sinhaparul.autolibbooks.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class BookDetailActivity extends AppCompatActivity {

    // Declare the instances:
    private Toolbar toolbar;
    private TextView name, author, genre, summary;
    private ImageView image;
    private Button favourite;
    private FloatingActionButton fabDownload;

    private ProgressDialog progress;

    private Book book;
    private boolean isFav = false;
    private SharedPreferences sp;
    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(null);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        image = (ImageView) findViewById(R.id.image);
        name = (TextView) findViewById(R.id.name);
        author = (TextView) findViewById(R.id.author);
        genre = (TextView) findViewById(R.id.genre);
        summary = (TextView) findViewById(R.id.summary);
        favourite = (Button) findViewById(R.id.favourite);
        fabDownload = (FloatingActionButton) findViewById(R.id.fab_download);

        progress = new ProgressDialog(this);

        sp = getSharedPreferences(Constants.SP_APP_DATA, MODE_PRIVATE);

        final Intent intent = getIntent();

        if(intent == null)
            finish();

        book = intent.getParcelableExtra("book_data");
        Log.i(Constants.LOG_TAG, "Name: "+book.getBookName());

        checkFav();

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(Constants.LOG_TAG, "Fav status: "+isFav);
                if(isFav){
                    removeFav(index);
                }
                else {
                    addToFav(book);
                }
            }
        });


        fabDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadBook(book.getEBookUrl());
            }
        });

    }




    private void populateUI() {

        name.setText(book.getBookName());
        author.setText(book.getAuthor());
        genre.setText(book.getGenre());
        //String thumbnail = Constants.IP + Constants.DIR + Constants.DIR_BOOK_IMG + book.getThumbnail();
        String thumbnail = book.getThumbnail();
        Glide.with(this).load(thumbnail).placeholder(R.drawable.placeholder).into(image);
        int genreBack ;
        switch (book.getGenre()){
            case "Fiction": genreBack = getResources().getColor(R.color.green); break;
            case "Mystery": genreBack = getResources().getColor(R.color.red); break;
            case "Classics": genreBack = getResources().getColor(R.color.dark); break;
            case "Motivational": genreBack = getResources().getColor(R.color.yellow); break;
            case "Romance": genreBack = getResources().getColor(R.color.pink); break;
            case "Science": genreBack = getResources().getColor(R.color.orange); break;
            case "Fantasy": genreBack = getResources().getColor(R.color.purple); break;
            default: genreBack = getResources().getColor(R.color.pink); break;
        }

        genre.setBackgroundColor(genreBack);

        if(!isFav){
            favourite.setText("Add favourite");
        }
        else {
            favourite.setText("Remove Favourite");
        }

        loadSummary();


    }



    private void loadSummary() {

        String url = Constants.IP + Constants.DIR + Constants.GET_SUMMARY;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i(Constants.LOG_TAG, response);
                if(progress.isShowing())
                    progress.dismiss();

                summary.setText(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BookDetailActivity.this, "Error: "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map getParams(){
                Map<String, String> map = new HashMap<>();
                map.put("book_id", book.getId());
                Log.i(Constants.LOG_TAG, "ID: "+ map.get("book_id"));
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }


    private void addToFav(Book book) {
        SharedPreferences.Editor editor = sp.edit();
        String favJson = sp.getString("fav_data", null);

        JSONArray favArray;

        try {
            if(favJson == null){
                favArray = new JSONArray();
            }else {
                favArray = new JSONArray(favJson);
            }

            JSONObject favObj = new JSONObject();
            favObj.put("id", book.getId());
            favObj.put("book_name", book.getBookName());
            favObj.put("author", book.getAuthor());
            favObj.put("genre", book.getGenre());
            favObj.put("thumbnail", book.getThumbnail());
            favObj.put("ebook_url", book.getEBookUrl());

            favArray.put(favObj);

            editor.putString("fav_data", favArray.toString());
            editor.apply();

            Log.i(Constants.LOG_TAG, "JSON: "+favArray.toString());

            Toast.makeText(this, "Added to your favourites", Toast.LENGTH_SHORT).show();

            isFav = true;
            favourite.setText("Remove favourite");

        } catch (JSONException e) {
                e.printStackTrace();
        }


    }

    private void checkFav() {

        progress.setMessage("Hang On! Loading Data...");
        progress.show();

        String jsonData = sp.getString("fav_data", null);
        if(jsonData != null){
            try {
                JSONArray favArray = new JSONArray(jsonData);
                for(int i = 0; i < favArray.length(); i++){
                    JSONObject obj = favArray.getJSONObject(i);
                    if(obj.get("id").equals(book.getId())){
                        isFav = true;
                        index = i;
                        break;
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        populateUI();

    }

    private void removeFav(int index) {
        SharedPreferences.Editor editor = sp.edit();
        String jsonData = sp.getString("fav_data", null);
        if(jsonData != null){
            try {
                JSONArray favArray = new JSONArray(jsonData);
                favArray.remove(index);
                editor.putString("fav_data", favArray.toString());
                editor.apply();
                isFav = false;
                favourite.setText("Add favourite");

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }


    private void downloadBook(String eBookUrl) {

        String url = Constants.IP + Constants.DIR + Constants.DIR_EBOOK + eBookUrl;

        Log.i(Constants.LOG_TAG, "EBOOK: "+url);

        String storagePath = Environment.getExternalStorageDirectory()
                .getPath()
                + "/autolib/";

        //Log.d("Strorgae in view",""+storagePath);

        File f = new File(storagePath);
        if (!f.exists()) {
            f.mkdirs();
        }

        //storagePath.mkdirs();
        String pathname = f.toString();
        if (!f.exists()) {
            f.mkdirs();
        }

        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        //Uri uri = Uri.parse(url);
        Uri uri = Uri.parse(url);



        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.allowScanningByMediaScanner();

        request.setTitle("Downloading: "+uri.getLastPathSegment());
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        
        if (uri.getLastPathSegment().endsWith("pdf"))
            request.setMimeType("application/pdf");

        request.setDestinationInExternalPublicDir("/autolib/", uri.getLastPathSegment());
        Long referese = dm.enqueue(request);


        Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_SHORT).show();

    }

}
