package com.example.anatoly.popularmovies;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.anatoly.popularmovies.Utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.MovieViewHolder>{
    private List<Movie> movies;
    private OnItemClickListener onItemClickListener;

    MoviesRecyclerAdapter(List<Movie> movies, OnItemClickListener listener) {
        this.movies = movies;
        this.onItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        final Uri picUri = NetworkUtils.buildImageUri(movie.getPosterPath(), NetworkUtils.IMAGE_SIZE.w185);

        Picasso.get()
                .load(picUri)
                .fit()
                .centerCrop()
                .into(holder.movieImage);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_item_layout, parent, false);
        return new MovieViewHolder(view);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView movieImage;

        MovieViewHolder(View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.iv_movie_pic);
            movieImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.itemClicked(getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
        void itemClicked(int position);
    }
}
