package com.example.android.moviebuzz;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.moviebuzz.Adapter.MovieRecyclerAdapter;
import com.example.android.moviebuzz.WebServices.MovieDataFetchAsync;
import com.example.android.moviebuzz.model.Movie;
import com.example.android.moviebuzz.utilities.NetworkUtils;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieDataFetchAsync.WebCallBack, MovieRecyclerAdapter.OnMovieClicked {
    private RecyclerView mRecyclerView;
    private MovieRecyclerAdapter mRecyclerAdapter;
    private static String mPopular = "popular";
    private static String mTopRated = "top_rated";
    private static String mCategory = "Popular Movies";
    private ProgressBar pb;
    private TextView tCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.rv_movies);
        pb = findViewById(R.id.pb_movies);
        tCategory = findViewById(R.id.category);
        tCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(mCategory.contains("Top"))){
                    fetchData(mTopRated);
                    mCategory = "Top Rated Movies";
                }else {
                    fetchData(mPopular);
                    mCategory = "Popular Movies";
                }
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerAdapter = new MovieRecyclerAdapter(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        fetchData(mPopular);
    }


    private void fetchData(String filter) {
        if(NetworkUtils.isNetworkAvailable(this)){
            showProgress();
            MovieDataFetchAsync service = new MovieDataFetchAsync(this, this);
            service.execute(NetworkUtils.buildUrl(filter, 1));
        }else{
            showAlertDialog("No Internet Connection! Please try again!!");
        }
    }

    private void loadViews(ArrayList<Movie> movies) {
        mRecyclerAdapter.setmMovieList(movies);
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    private void showProgress() {
        pb.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    private void showMovies() {
        pb.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
        tCategory.setText(mCategory);
    }

    private void showAlertDialog(String message){
        (new AlertDialog.Builder(this).setTitle("Alert !!").setMessage(message).setNegativeButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).create()).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItem = item.getItemId();
        switch (menuItem) {
            case R.id.action_popular:
                mCategory = "Popular Movies";
                fetchData(mPopular);
                break;
            case R.id.action_top_rated:
                mCategory = "Top Rated Movies";
                fetchData(mTopRated);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onFetchingData(ArrayList<Movie> list) {
        if (list != null) {
            showMovies();
            loadViews(list);
        }
    }

    @Override
    public void movieClicked(Movie selectedMovie) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("movie", selectedMovie);
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }
}
