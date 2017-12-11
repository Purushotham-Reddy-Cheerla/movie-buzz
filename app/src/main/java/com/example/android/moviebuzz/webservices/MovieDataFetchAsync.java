package com.example.android.moviebuzz.webservices;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.example.android.moviebuzz.model.Movie;
import com.example.android.moviebuzz.utilities.MovieUtils;
import com.example.android.moviebuzz.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by purus on 11/25/2017.
 */

@SuppressWarnings("ALL")
public class MovieDataFetchAsync extends AsyncTask<URL, Void, ArrayList<Movie>> {

    private ArrayList<Movie> list = new ArrayList<>();

    private final WebCallBack callBack;

    public interface WebCallBack {
        void onFetchingData(ArrayList<Movie> list);
    }

    public MovieDataFetchAsync(Context context, WebCallBack handler) {
        Context mContext = context;
        callBack = handler;
    }

    @Override
    protected ArrayList<Movie> doInBackground(URL... urls) {
        URL pUrl = urls[0];
        try {
            String pData = NetworkUtils.getMovieData(pUrl);
            if (pData != null) {
                list = MovieUtils.getMovieData(pData);
            } else {
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);
        callBack.onFetchingData(movies);
    }
}
