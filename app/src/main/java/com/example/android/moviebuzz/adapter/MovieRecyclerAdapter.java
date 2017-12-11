package com.example.android.moviebuzz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.moviebuzz.R;
import com.example.android.moviebuzz.model.Movie;
import com.example.android.moviebuzz.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by purus on 11/26/2017.
 */

@SuppressWarnings("ALL")
public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MovieHolder> {

    private final ArrayList<Movie> mMovieList = new ArrayList<>();
    private final Context mContext;
    private final OnMovieClicked callBack;


    public interface OnMovieClicked {
        void movieClicked(Movie selectedMovie);
    }

    public MovieRecyclerAdapter(Context context, OnMovieClicked handler) {
        callBack = handler;
        mContext = context;
    }


    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_list_item, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        Picasso.with(mContext).load(NetworkUtils.IMAGE_URL + mMovieList.get(position).posterPath).into(holder.mPoster);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView mPoster;

        private MovieHolder(View itemView) {
            super(itemView);
            mPoster = itemView.findViewById(R.id.iv_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            callBack.movieClicked(mMovieList.get(getAdapterPosition()));
        }
    }

    public void setMovieList(ArrayList<Movie> list) {
        mMovieList.addAll(list);
        notifyDataSetChanged();
    }

}
