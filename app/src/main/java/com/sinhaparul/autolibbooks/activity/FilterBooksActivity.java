package com.sinhaparul.autolibbooks.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.sinhaparul.autolibbooks.Constants;
import com.sinhaparul.autolibbooks.R;
import com.sinhaparul.autolibbooks.adapter.BookLibraryAdapter;
import com.sinhaparul.autolibbooks.model.Book;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class FilterBooksActivity extends AppCompatActivity {

    // Declare the instances:
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ArrayList<Book> bookList;
    private BookLibraryAdapter adapter;
    private ProgressDialog progress;
    private TextView tvType,tvName;

    private ArrayList<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_books);

        tvName = (TextView) findViewById(R.id.name);
        tvType = (TextView) findViewById(R.id.type);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(null);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        if(intent == null)
            finish();

        String type = intent.getStringExtra("type");
        String name = intent.getStringExtra("name");

        tvName.setText(name);

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

            tvType.setText("Books by: ");

            for(int i = 0; i < data.size(); i++){
                if(data.get(i).getAuthor().equals(name))
                    books.add(data.get(i));
            }

        }
        else if(type.equals(Constants.TYPE_GENRE)){

            tvType.setText("Books of genre: ");

            for(int i = 0; i < data.size(); i++){
                if(data.get(i).getGenre().equals(name))
                    books.add(data.get(i));
            }

        }


        return books;
    }
}
