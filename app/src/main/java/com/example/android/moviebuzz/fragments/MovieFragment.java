package com.example.android.moviebuzz.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.android.moviebuzz.MovieDetailActivity;
import com.example.android.moviebuzz.R;
import com.example.android.moviebuzz.adapter.MovieRecyclerAdapter;
import com.example.android.moviebuzz.adapter.ScrollListener;
import com.example.android.moviebuzz.model.Movie;
import com.example.android.moviebuzz.utilities.MovieUtils;
import com.example.android.moviebuzz.utilities.NetworkUtils;
import com.example.android.moviebuzz.webservices.MovieDataFetchAsync;

import java.util.ArrayList;

/**
 * Created by purus on 12/10/2017.
 */

@SuppressWarnings("ALL")
public class MovieFragment extends Fragment implements MovieRecyclerAdapter.OnMovieClicked,
        MovieDataFetchAsync.WebCallBack, ScrollListener.LoadNextPage {

    private static final String filterKey = "filter";

    private int mPage = 1;
    private String mFilter;

    private ProgressBar mProgressBar;
    private MovieRecyclerAdapter mAdapter;

    public MovieFragment() {

    }

    public static MovieFragment newInstance(String filter) {
        Bundle info = new Bundle();
        info.putString(filterKey, filter);
        MovieFragment newFragment = new MovieFragment();
        newFragment.setArguments(info);
        return newFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mFilter = bundle.getString(filterKey);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        fetchMovieData(1);
    }

    private void fetchMovieData(int page) {
        if (NetworkUtils.isNetworkAvailable(getContext())) {
            MovieDataFetchAsync task = new MovieDataFetchAsync(getContext(), this);
            task.execute(NetworkUtils.buildUrl(mFilter, page));
        } else {
            MovieUtils.showAlertDialog(getContext(), getString(R.string.no_internet));
        }
    }

    private void initView(View view) {
        mProgressBar = view.findViewById(R.id.pb_movies);
        RecyclerView mRecyclerView = view.findViewById(R.id.rv_movies);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnScrollListener(new ScrollListener(manager, this));
        mAdapter = new MovieRecyclerAdapter(getContext(), this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void movieClicked(Movie selectedMovie) {
        Bundle data = new Bundle();
        data.putParcelable("movie", selectedMovie);
        Intent newIntent = new Intent(getContext(), MovieDetailActivity.class);
        newIntent.putExtra("bundle", data);
        startActivity(newIntent);
    }

    @Override
    public void onFetchingData(ArrayList<Movie> list) {
        mProgressBar.setVisibility(View.GONE);
        mAdapter.setMovieList(list);
    }

    @Override
    public void loadNext() {
        fetchMovieData(++mPage);
    }
}
