package com.mobiledi.mgk.cinemawiki.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobiledi.mgk.cinemawiki.DetailViewActivity;
import com.mobiledi.mgk.cinemawiki.HomeActivity;
import com.mobiledi.mgk.cinemawiki.R;
import com.mobiledi.mgk.cinemawiki.model.Cinema;
import com.mobiledi.mgk.cinemawiki.rest.ClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Nirmalyam on 11/12/2017.
 */

public class CinemaAdapter extends RecyclerView.Adapter<CinemaAdapter.CinemaViewHolder> {
    private List<Cinema> cinema;
    private int rowLayout;
    private Context context;


    static class CinemaViewHolder extends RecyclerView.ViewHolder {
        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView rating;
        ImageView poster;
        private ClickListener clickListener;
        public CinemaViewHolder(View v) {
            super(v);
            moviesLayout = (LinearLayout) v.findViewById(R.id.movies_layout);
            movieTitle = (TextView) v.findViewById(R.id.title);
            rating = (TextView) v.findViewById(R.id.rating);
            poster=(ImageView)v.findViewById(R.id.img_android);
        }
        public void setClickListener(ClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        public void onClick(View view) {
            clickListener.onClick(view, getPosition());
        }
    }

    public CinemaAdapter(List<Cinema> cinema, int rowLayout, Context context) {
        this.cinema = cinema;
        this.rowLayout = rowLayout;
        this.context = context;
    }
    @Override
    public CinemaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(rowLayout,parent,false);
        return  new CinemaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CinemaViewHolder holder, int position) {
        holder.movieTitle.setText("Movie Title : "+cinema.get(position).getTitle());
         // holder.data.setText(cinema.get(position).getReleaseDate());
        //  holder.movieDescription.setText(cinema.get(position).getOverview());
        holder.rating.setText("Rating: "+cinema.get(position).getVoteAverage().toString());
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185/"+cinema.get(position).getPosterPath()).resize(120,60).into(holder.poster);

    }



    @Override
    public int getItemCount() {
        return cinema.size();
    }
}
