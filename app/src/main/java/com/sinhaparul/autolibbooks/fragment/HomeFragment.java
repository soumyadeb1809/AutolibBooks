package com.sinhaparul.autolibbooks.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sinhaparul.autolibbooks.R;
import com.sinhaparul.autolibbooks.activity.MainActivity;
import com.sinhaparul.autolibbooks.adapter.AuthorAdapter;
import com.sinhaparul.autolibbooks.adapter.BookLibraryAdapter;
import com.sinhaparul.autolibbooks.adapter.GenreAdapter;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;


/**
 * A simple {@link Fragment} subclass.
 */

public class HomeFragment extends Fragment {

    // Declare the instances:
    private RecyclerView genreRecycler;
    private GenreAdapter genreAdapter;

    private RecyclerView featuredRecycler;
    private BookLibraryAdapter featuredAdapter;

    private RecyclerView authorRecycler;
    private AuthorAdapter authorAdapter;


    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize the UI instances:
        featuredRecycler = (RecyclerView) mView.findViewById(R.id.featured_recycler);
        genreRecycler = (RecyclerView) mView.findViewById(R.id.genres_recycler);
        authorRecycler = (RecyclerView) mView.findViewById(R.id.author_recycler);

        // For populating Genre RecyclerView:
        genreAdapter = new GenreAdapter(getActivity(), MainActivity.genreList);
        genreRecycler.setAdapter(genreAdapter);
        genreRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        OverScrollDecoratorHelper.setUpOverScroll(genreRecycler, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);

        // For populating Featured Books RecyclerView:
        featuredAdapter = new BookLibraryAdapter(getActivity(), MainActivity.featuredBookList);
        featuredRecycler.setAdapter(featuredAdapter);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        OverScrollDecoratorHelper.setUpOverScroll(featuredRecycler, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);

        // For populating Authors RecyclerView:
        authorAdapter = new AuthorAdapter(getActivity(), MainActivity.authorList);
        authorRecycler.setAdapter(authorAdapter);
        authorRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        OverScrollDecoratorHelper.setUpOverScroll(authorRecycler, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);

        //Log.i(Constants.LOG_TAG, "Test: "+ MainActivity.genreList.get(0).getName());

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }



}
