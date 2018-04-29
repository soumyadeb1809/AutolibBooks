package com.sinhaparul.autolibbooks.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sinhaparul.autolibbooks.Constants;
import com.sinhaparul.autolibbooks.R;
import com.sinhaparul.autolibbooks.adapter.BookLibraryAdapter;
import com.sinhaparul.autolibbooks.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Book> bookList;
    private BookLibraryAdapter adapter;
    private ProgressDialog progress;


    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_favourite, container, false);

        recyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);
        bookList = new ArrayList<>();

        adapter = new BookLibraryAdapter(getActivity(), bookList);
        RecyclerView.LayoutManager mLayout= new GridLayoutManager(getContext(), 3);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(mLayout);
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);


        progress = new ProgressDialog(getContext());

        populateUI();


        return mView;
    }

    private void populateUI() {
        SharedPreferences sp = getContext().getSharedPreferences(Constants.SP_APP_DATA, Context.MODE_PRIVATE);

        String favJson = sp.getString("fav_data", null);

        if(favJson != null){

            try {
                JSONArray favArray = new JSONArray(favJson);

                for (int i = 0; i < favArray.length(); i++){
                    JSONObject obj = favArray.getJSONObject(i);
                    String id = obj.getString("id");
                    String name = obj.getString("book_name");
                    String author = obj.getString("author");
                    String genre = obj.getString("genre");
                    String thumbnail = obj.getString("thumbnail");
                    String eBookUrl = obj.getString("ebook_url");

                    Book book = new Book(id, name,author, genre, thumbnail, eBookUrl);
                    bookList.add(book);
                    adapter.notifyDataSetChanged();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

}
