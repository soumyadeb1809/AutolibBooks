package com.soumyadeb.autolibbooks.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soumyadeb.autolibbooks.activity.BookDetailActivity;
import com.soumyadeb.autolibbooks.model.Book;
import com.soumyadeb.autolibbooks.Constants;
import com.soumyadeb.autolibbooks.R;

import java.util.List;

public class BookLibraryAdapter extends RecyclerView.Adapter<BookLibraryAdapter.MyViewHolder> {

    private Context mContext;
    private List<Book> bookList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView bookName, author, genre;
        public ImageView bookImg;
        public View v;
        public MyViewHolder(View view) {
            super(view);
            v = view;
            bookName = (TextView)view.findViewById(R.id.book_name);
            author = (TextView)view.findViewById(R.id.author);
            genre = (TextView)view.findViewById(R.id.genre);
            bookImg = (ImageView)view.findViewById(R.id.img_book);

        }

    }

    public BookLibraryAdapter(Context mContext, List<Book> bookList)
    {
        this.mContext=mContext;
        this.bookList = bookList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item_card2,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final BookLibraryAdapter.MyViewHolder holder, int position)
    {
        final Book book = bookList.get(position);
        holder.bookName.setText(book.getBookName());
        holder.author.setText(book.getAuthor());
        holder.genre.setText(book.getGenre());
        holder.genre.setAllCaps(true);
        //Log.i(Constants.LOG_TAG, "Thumb: "+book.getThumbnail());
        String thumbnail = Constants.IP + Constants.DIR + Constants.DIR_BOOK_IMG + book.getThumbnail();
        Glide.with(mContext).load(thumbnail).placeholder(R.drawable.placeholder).into(holder.bookImg);
       // holder.thumbnail.setImageResource(album.getThumbnail());
        int genreBack ;

        switch (book.getGenre()){
        case "Fiction": genreBack = mContext.getResources().getColor(R.color.green); break;
        case "Mystery": genreBack = mContext.getResources().getColor(R.color.red); break;
        case "Classics": genreBack = mContext.getResources().getColor(R.color.dark); break;
        case "Motivational": genreBack = mContext.getResources().getColor(R.color.yellow); break;
        case "Romance": genreBack = mContext.getResources().getColor(R.color.pink); break;
        case "Science": genreBack = mContext.getResources().getColor(R.color.orange); break;
        case "Fantasy": genreBack = mContext.getResources().getColor(R.color.purple); break;
        default: genreBack = mContext.getResources().getColor(R.color.pink); break;
    }

        holder.genre.setBackgroundColor(genreBack);

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BookDetailActivity.class);
                intent.putExtra("book_data", book);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }


}