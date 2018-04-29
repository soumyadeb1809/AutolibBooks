package com.sinhaparul.autolibbooks.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sinhaparul.autolibbooks.Constants;
import com.sinhaparul.autolibbooks.R;
import com.sinhaparul.autolibbooks.activity.FilterBooksActivity;
import com.sinhaparul.autolibbooks.activity.MainActivity;
import com.sinhaparul.autolibbooks.model.Genre;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.MyViewHolder> {

    private Context mContext;
    private List<Genre> genreList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView image;
        public View filter;
        public  View v;

        public MyViewHolder(View view) {
            super(view);
            v = view;
            name = (TextView)view.findViewById(R.id.genre);
            image = (ImageView)view.findViewById(R.id.img_genre_back);
            filter = view.findViewById(R.id.filter_genre_back);
        }

    }

    public GenreAdapter(Context mContext, List<Genre> genreList)
    {
        this.mContext=mContext;
        this.genreList = genreList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_item_card,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final GenreAdapter.MyViewHolder holder, int position)
    {
        final Genre genre = genreList.get(position);
        holder.name.setText(genre.getName());

        //Log.i(Constants.LOG_TAG, "Thumb: "+genre.getImage());

        //String thumbnail = Constants.IP + Constants.DIR + Constants.DIR_GENRE_IMG + genre.getImage();
        String thumbnail = genre.getImage();

        Glide.with(mContext).load(thumbnail).placeholder(R.drawable.placeholder).into(holder.image);
       // holder.thumbnail.setImageResource(album.getThumbnail());
        int genreBack ;

        Log.i(Constants.LOG_TAG, thumbnail);
        Log.i(Constants.LOG_TAG, genre.getFilter());

        switch (genre.getFilter()){
            case "green": genreBack = mContext.getResources().getColor(R.color.green); break;
            case "red": genreBack = mContext.getResources().getColor(R.color.red); break;
            case "dark": genreBack = mContext.getResources().getColor(R.color.dark); break;
            case "yellow": genreBack = mContext.getResources().getColor(R.color.yellow); break;
            case "pink": genreBack = mContext.getResources().getColor(R.color.pink); break;
            case "orange": genreBack = mContext.getResources().getColor(R.color.orange); break;
            case "purple": genreBack = mContext.getResources().getColor(R.color.purple); break;
            default: genreBack = mContext.getResources().getColor(R.color.pink); break;
        }

        holder.filter.setBackgroundColor(genreBack);
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FilterBooksActivity.class);
                intent.putExtra("type", Constants.TYPE_GENRE);
                intent.putExtra("name", genre.getName());
                intent.putExtra("data", MainActivity.bookList);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }


}