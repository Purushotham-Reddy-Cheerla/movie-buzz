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

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MovieHolder> {

    private ArrayList<Movie> mMovieList;
    private Context context;

    private OnMovieClicked callBack;




    public interface OnMovieClicked {
        void movieClicked(Movie selectedMovie);
    }

    public MovieRecyclerAdapter(OnMovieClicked handler){
        callBack = handler;
    }


    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        Picasso.with(context).load(NetworkUtils.IMAGE_URL+mMovieList.get(position).posterPath).into(holder.mPoster);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
     /*   private TextView mTitle;
        private TextView mRelease;
        private TextView mRating;
        private TextView mOverView;*/
        private ImageView mPoster;
        private MovieHolder(View itemView) {
            super(itemView);
            mPoster = (ImageView) itemView.findViewById(R.id.iv_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            callBack.movieClicked(mMovieList.get(getAdapterPosition()));
        }
    }

    public void setmMovieList(ArrayList<Movie> list){
        mMovieList = list;
        notifyDataSetChanged();
    }
}
