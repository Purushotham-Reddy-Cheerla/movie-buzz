package com.example.android.moviebuzz;

import android.content.Intent;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.moviebuzz.model.Movie;
import com.example.android.moviebuzz.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {
    private ImageView thumbNail;
    private Movie mMovie;
    static final String STATE_MOVIE = "movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        thumbNail = findViewById(R.id.iv_thumb);
        if (savedInstanceState != null) {
            mMovie = savedInstanceState.getParcelable(STATE_MOVIE);
            showMovieDetails(mMovie);
        }else{
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
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(movie.originalTitle);
        }
        Picasso.with(this).load(NetworkUtils.IMAGE_URL + movie.posterPath).into(thumbNail);
        ((TextView) findViewById(R.id.tv_title)).setText(movie.originalTitle);
        ((TextView) findViewById(R.id.tv_date)).setText(movie.releaseDate);
        ((RatingBar) findViewById(R.id.rb_user)).setRating((float) movie.userRating / 2);
        ((TextView) findViewById(R.id.tv_overview)).setText(movie.review);
    }
}
