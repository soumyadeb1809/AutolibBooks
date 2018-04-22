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
import com.soumyadeb.autolibbooks.Constants;
import com.soumyadeb.autolibbooks.R;
import com.soumyadeb.autolibbooks.activity.FilterBooksActivity;
import com.soumyadeb.autolibbooks.activity.MainActivity;
import com.soumyadeb.autolibbooks.model.Author;

import java.util.List;

public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.MyViewHolder> {

    private Context mContext;
    private List<Author> authorList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView image;
        public View v;

        public MyViewHolder(View view) {
            super(view);
            v = view;
            name = (TextView)view.findViewById(R.id.name);
            image = (ImageView)view.findViewById(R.id.image);
        }

    }

    public AuthorAdapter(Context mContext, List<Author> authorList)
    {
        this.mContext=mContext;
        this.authorList = authorList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.author_item_card,parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final AuthorAdapter.MyViewHolder holder, int position)
    {
        final Author author = authorList.get(position);
        holder.name.setText(author.getName());

        String image = Constants.IP + Constants.DIR + Constants.DIR_AUTHOR_IMG + author.getImage();
        Glide.with(mContext).load(image).placeholder(R.drawable.placeholder).into(holder.image);

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FilterBooksActivity.class);
                intent.putExtra("type", Constants.TYPE_AUTHOR);
                intent.putExtra("name", author.getName());
                intent.putExtra("data", MainActivity.bookList);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return authorList.size();
    }


}