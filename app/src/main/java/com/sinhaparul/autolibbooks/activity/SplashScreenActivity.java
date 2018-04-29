package com.sinhaparul.autolibbooks.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sinhaparul.autolibbooks.Constants;
import com.sinhaparul.autolibbooks.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashScreenActivity extends AppCompatActivity {

    // Declare the instances:
    private SharedPreferences sp;

    private ProgressBar progressBar;
    private TextView offline;

    private static final int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        offline = (TextView) findViewById(R.id.offline);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Initialize the instances:
        sp = getSharedPreferences(Constants.SP_NAME, MODE_PRIVATE);

        // Check if user is logged in:
        checkUserLogin();

    }


    // Method to check if user is logged in:
    private void checkUserLogin() {
        String status = sp.getString("status", "false");
        if(!status.equals("true")){
            startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
            finish();
        }
        else {

            if(isNetworkAvailable()) {

                fetchData();
            }
            else {
                Toast.makeText(this, "No network connection", Toast.LENGTH_LONG).show();
                offline.setVisibility(View.VISIBLE);
                startSplash();
            }
        }
    }

    private void fetchData() {

        String url = Constants.IP + Constants.DIR + Constants.GET_DATA;

        Log.i(Constants.LOG_TAG, "DATA IP :"+url);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    SharedPreferences dataSP = getSharedPreferences(Constants.SP_APP_DATA, MODE_PRIVATE);
                    SharedPreferences.Editor editor = dataSP.edit();

                    JSONObject mainObj = new JSONObject(response);

                    JSONArray bookDataArray = mainObj.getJSONArray("book_data");
                    editor.putString("book_data", bookDataArray.toString());

                    JSONArray featuredBookArray = mainObj.getJSONArray("featured_books");
                    editor.putString("featured_books", featuredBookArray.toString());

                    JSONArray authorArray = mainObj.getJSONArray("authors");
                    editor.putString("authors", authorArray.toString());

                    JSONArray genreArray = mainObj.getJSONArray("genres");
                    editor.putString("genres", genreArray.toString());

                    editor.apply();

                    startSplash();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SplashScreenActivity.this, "Error: "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void startSplash() {

        progressBar.setVisibility(View.INVISIBLE);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                finish();

            }
        }, SPLASH_TIME_OUT);
    }


}
