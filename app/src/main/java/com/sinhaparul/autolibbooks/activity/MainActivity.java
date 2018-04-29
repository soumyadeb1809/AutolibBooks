package com.sinhaparul.autolibbooks.activity;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;

import com.sinhaparul.autolibbooks.Constants;
import com.sinhaparul.autolibbooks.R;
import com.sinhaparul.autolibbooks.fragment.BookLibraryFragment;
import com.sinhaparul.autolibbooks.fragment.FavouriteFragment;
import com.sinhaparul.autolibbooks.fragment.HomeFragment;
import com.sinhaparul.autolibbooks.fragment.ProfileFragment;
import com.sinhaparul.autolibbooks.model.Author;
import com.sinhaparul.autolibbooks.model.Book;
import com.sinhaparul.autolibbooks.model.Genre;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Declare the instances:
    private Toolbar toolbar;
    private BottomNavigationView navigation;

    private SharedPreferences sp;

    public static ArrayList<Book> bookList;
    public static ArrayList<Book> featuredBookList;
    public static ArrayList<Genre> genreList;
    public static ArrayList<Author> authorList;

    // OnClick handler for Bottom Navigation:
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if(item.getItemId() != navigation.getSelectedItemId()) {
                switch (item.getItemId()) {
                    case R.id.navigation_dashboard:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content, new HomeFragment()).commit();
                        return true;
                    case R.id.navigation_browse_books:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content, new BookLibraryFragment()).commit();
                        return true;

                    case R.id.navigation_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content, new ProfileFragment()).commit();
                        return true;

                    case R.id.navigation_favourites:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content, new FavouriteFragment()).commit();
                        return true;


                }
            }
            setSupportActionBar(toolbar);
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(null);
        setSupportActionBar(toolbar);


        // Initialize instances:
        sp = getSharedPreferences(Constants.SP_NAME, MODE_PRIVATE);


        // Bottom Navigation:
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        parseData();

        getSupportFragmentManager().beginTransaction().replace(R.id.content, new HomeFragment()).commit();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        Log.i(Constants.LOG_TAG, "Width :"+displayMetrics.widthPixels);


    }

    private void parseData() {
        // Initialize the objects:
        bookList = new ArrayList<>();
        featuredBookList = new ArrayList<>();
        genreList = new ArrayList<>();
        authorList = new ArrayList<>();

        SharedPreferences dataSP = getSharedPreferences(Constants.SP_APP_DATA, MODE_PRIVATE);

        String bookData = dataSP.getString("book_data", null);
        String featuredBooks = dataSP.getString("featured_books", null);
        String genres = dataSP.getString("genres", null);
        String authors = dataSP.getString("authors", null);

            try {
                if(bookData != null) {
                    JSONArray bookDataArray = new JSONArray(bookData);
                    for (int i = 0; i < bookDataArray.length(); i++) {
                        JSONObject bookObj = bookDataArray.getJSONObject(i);
                        String id = bookObj.getString("book_id");
                        String name = bookObj.getString("book_name");
                        String author = bookObj.getString("book_author");
                        String genre = bookObj.getString("book_genre");
                        String thumbnail = bookObj.getString("thumbnail");
                        String eBookUrl = bookObj.getString("ebook_url");

                        Book book = new Book(id, name, author, genre, thumbnail, eBookUrl);
                        bookList.add(book);
                    }
                }

                if(featuredBooks != null) {
                    JSONArray bookDataArray = new JSONArray(featuredBooks);
                    for (int i = 0; i < bookDataArray.length(); i++) {
                        JSONObject bookObj = bookDataArray.getJSONObject(i);
                        String id = bookObj.getString("book_id");
                        String name = bookObj.getString("book_name");
                        String author = bookObj.getString("book_author");
                        String genre = bookObj.getString("book_genre");
                        String thumbnail = bookObj.getString("thumbnail");
                        String eBookUrl = bookObj.getString("ebook_url");

                        Book book = new Book(id, name, author, genre, thumbnail, eBookUrl);
                        featuredBookList.add(book);
                    }
                }


                if(genres != null) {
                    Log.i(Constants.LOG_TAG, "Test Passed");
                    JSONArray genreArray = new JSONArray(genres);
                    for (int i = 0; i < genreArray.length(); i++) {
                        JSONObject genreObj = genreArray.getJSONObject(i);

                        String id = genreObj.getString("id");
                        String name = genreObj.getString("name");
                        String image = genreObj.getString("image");
                        String filter = genreObj.getString("filter");

                        Genre genre = new Genre(id, name, filter, image);
                        genreList.add(genre);
                    }
                }
                else {

                    Log.i(Constants.LOG_TAG, "Test Failed");

                }

                if(authors != null) {
                    JSONArray authorArray = new JSONArray(authors);
                    for (int i = 0; i < authorArray.length(); i++) {
                        JSONObject genreObj = authorArray.getJSONObject(i);

                        String id = genreObj.getString("id");
                        String name = genreObj.getString("name");
                        String image = genreObj.getString("image");

                        Author author = new Author(id, name, image);
                        authorList.add(author);
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }



    }


}
