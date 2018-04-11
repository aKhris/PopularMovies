package com.example.anatoly.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anatoly.popularmovies.Utils.NetworkUtils;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView originalTitleTextView = findViewById(R.id.tv_details_original_title);
        ImageView moviePosterImageView = findViewById(R.id.iv_details_movie_poster);
        TextView overviewTextView = findViewById(R.id.tv_details_overview);
        TextView voteAverageTextView = findViewById(R.id.tv_details_vote_average);
        TextView releaseDateTextView = findViewById(R.id.tv_details_release_date);

        if(getIntent().hasExtra(Movie.TAG)){
            Movie movie = (Movie) getIntent().getSerializableExtra(Movie.TAG);
            originalTitleTextView.setText(movie.getOriginalTitle());
            Picasso.get().load(NetworkUtils.buildImageUri(movie.getPosterPath(), NetworkUtils.IMAGE_SIZE.w500))
                    .into(moviePosterImageView);
            overviewTextView.setText(movie.getOverview());
            String voteString = String.valueOf(movie.getVoteAvr())+"/10";
            voteAverageTextView.append(" ");
            voteAverageTextView.append(voteString);
            releaseDateTextView.append(" ");
            releaseDateTextView.append(movie.getReleaseDate());
        }
    }
}
