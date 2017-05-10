package com.example.sandipghosh.zersey;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandipghosh on 09/05/17.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    List<Card> items;

    public CardAdapter(String[] title,String[] category,String[] description,String[] urls, Bitmap[] images) {
        super();
        items = new ArrayList<Card>();
        for (int i = 0; i < title.length; i++) {
            Card item = new Card();
            item.setTitle(title[i]);
            item.setCategory(category[i]);
            item.setDescription(description[i]);
            item.setUrl(urls[i]);
            item.setImage(images[i]);
            items.add(item);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Card list = items.get(position);
        holder.imageView.setImageBitmap(list.getImage());
        holder.title.setText(list.getTitle());
        holder.description.setText(list.getDescription());
        holder.category.setText(list.getCategory());
        holder.textViewUrl.setText(list.getUrl());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView title;
        public TextView category;
        public TextView description;
        public TextView textViewUrl;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.image);
            category = (TextView) itemView.findViewById(R.id.category);

            description = (TextView) itemView.findViewById(R.id.description);

            title = (TextView) itemView.findViewById(R.id.title);

            textViewUrl = (TextView) itemView.findViewById(R.id.url);

        }
    }
}