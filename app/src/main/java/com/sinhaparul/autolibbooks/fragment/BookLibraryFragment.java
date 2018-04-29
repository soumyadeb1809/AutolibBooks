package com.sinhaparul.autolibbooks.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sinhaparul.autolibbooks.activity.MainActivity;
import com.sinhaparul.autolibbooks.model.Book;
import com.sinhaparul.autolibbooks.R;
import com.sinhaparul.autolibbooks.adapter.BookLibraryAdapter;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookLibraryFragment extends Fragment {


    private RecyclerView recyclerView;
    private ArrayList<Book> bookList;
    private BookLibraryAdapter adapter;
    private ProgressDialog progress;

    public BookLibraryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_book_library, container, false);

        recyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);
        bookList = new ArrayList<>();

        adapter = new BookLibraryAdapter(getActivity(), MainActivity.bookList);
        RecyclerView.LayoutManager mLayout= new GridLayoutManager(getContext(), 3);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(mLayout);
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);


        progress = new ProgressDialog(getContext());

        //fetchBooks();

        return mView;
    }

    /*
    private void fetchBooks() {

        String url = Constants.IP + Constants.DIR + Constants.GET_BOOKS;

        progress.setMessage("Hang on. Fetching your data...");
        progress.setCancelable(false);
        progress.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(progress.isShowing()){
                    progress.dismiss();
                }

                try {

                    Log.i(Constants.LOG_TAG, response);
                    JSONArray dataArray = new JSONArray(response);

                    for(int i = 0; i < dataArray.length(); i++){
                        JSONObject obj = dataArray.getJSONObject(i);
                        String bookName = obj.getString("book_name");
                        String author = obj.getString("book_author");
                        String genre = obj.getString("book_genre");
                        String thumbnail =  obj.getString("thumbnail");

                        thumbnail = Constants.IP + Constants.DIR + "/img/books/" + thumbnail;

                        Book book = new Book(bookName, author, genre, thumbnail);

                        bookList.add(book);
                        adapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error: "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);

    }

    static class BottomOffsetDecoration extends RecyclerView.ItemDecoration {
        private int mBottomOffset, mTopOffset;

        public BottomOffsetDecoration(int bottomOffset, int topOffset) {
            mBottomOffset = bottomOffset;
            mTopOffset = topOffset;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            int dataSize = state.getItemCount();
            int position = parent.getChildAdapterPosition(view);
            //--------------- FOR GRID LAYOUT -------------------
            GridLayoutManager grid = (GridLayoutManager)parent.getLayoutManager();
            if ((dataSize - position) <= grid.getSpanCount()) {
                outRect.set(0, mTopOffset, 0, mBottomOffset);
            } else {
                outRect.set(0, 0, 0, 0);
            }

            /*------------- FOR LINEAR LAYOUT-------------------

            if (dataSize > 0 && position == dataSize - 1) {
                outRect.set(0, 0, 0, mBottomOffset);
            } else {
                outRect.set(0, 0, 0, 0);
            }

            */

    //    }

    //}




}
