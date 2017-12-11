package com.example.android.moviebuzz;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.android.moviebuzz.model.Movie;
import com.example.android.moviebuzz.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {
    private ImageView thumbNail;
    private Movie mMovie;
    private static final String STATE_MOVIE = "movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        thumbNail = findViewById(R.id.iv_thumb);
        if (savedInstanceState != null) {
            mMovie = savedInstanceState.getParcelable(STATE_MOVIE);
            showMovieDetails(mMovie);
        } else {
            getData();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(STATE_MOVIE, mMovie);
        super.onSaveInstanceState(outState);
    }

    private void getData() {
        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            if (receivedIntent.hasExtra("bundle")) {
                Bundle bundle = receivedIntent.getBundleExtra("bundle");
                Movie selectedMovie = bundle.getParcelable("movie");
                mMovie = selectedMovie;
                showMovieDetails(selectedMovie);
            }
        }
    }

    private void showMovieDetails(Movie movie) {
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle(movie.originalTitle);
            bar.setDisplayHomeAsUpEnabled(false);
        }
        Picasso.with(this).load(NetworkUtils.IMAGE_URL + movie.posterPath).into(thumbNail);
        ((TextView) findViewById(R.id.tv_title)).setText(movie.originalTitle);
        ((TextView) findViewById(R.id.tv_date)).setText(movie.releaseDate);
        ((RatingBar) findViewById(R.id.rb_user)).setRating((float) movie.userRating / 2);
        ((TextView) findViewById(R.id.tv_overview)).setText(movie.review);
    }

    @Override
    public boolean shouldUpRecreateTask(Intent targetIntent) {
        return false;
    }
}
