package com.soumyadeb.autolibbooks.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.soumyadeb.autolibbooks.Constants;
import com.soumyadeb.autolibbooks.R;
import com.soumyadeb.autolibbooks.activity.MainActivity;
import com.soumyadeb.autolibbooks.adapter.BookLibraryAdapter;
import com.soumyadeb.autolibbooks.model.Book;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class FilterBooksActivity extends AppCompatActivity {

    // Declare the instances:
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ArrayList<Book> bookList;
    private BookLibraryAdapter adapter;
    private ProgressDialog progress;

    private ArrayList<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_books);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(null);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        if(intent == null)
            finish();

        String type = intent.getStringExtra("type");
        String name = intent.getStringExtra("name");
        ArrayList<Book> data = intent.getParcelableArrayListExtra("data");

        bookList = getFilteredBooks(type, name, data);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        adapter = new BookLibraryAdapter(this, bookList);
        RecyclerView.LayoutManager mLayout= new GridLayoutManager(this, 3);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(mLayout);
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);


    }

    private ArrayList<Book> getFilteredBooks(String type, String name, ArrayList<Book> data) {
        ArrayList<Book> books = new ArrayList<>();

        if(type.equals(Constants.TYPE_AUTHOR)){

            for(int i = 0; i < data.size(); i++){
                if(data.get(i).getAuthor().equals(name))
                    books.add(data.get(i));
            }

        }
        else if(type.equals(Constants.TYPE_GENRE)){

            for(int i = 0; i < data.size(); i++){
                if(data.get(i).getGenre().equals(name))
                    books.add(data.get(i));
            }

        }


        return books;
    }
}
