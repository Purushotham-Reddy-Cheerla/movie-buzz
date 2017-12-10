package com.example.android.moviebuzz.utilities;

import android.util.Log;

import com.example.android.moviebuzz.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by purus on 11/25/2017.
 */

public final class MovieUtils {

    private static final String mResults = "results";
    private static final String mPosterPath = "poster_path";
    private static final String mOriginalTitle = "original_title";
    private static final String mReleaseDate = "release_date";
    private static final String mUserRating = "vote_average";
    private static final String mReview = "overview";

    public static ArrayList<Movie> getMovieData(String response) throws JSONException {
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        JSONArray array;
        JSONObject mObject;
        JSONObject jsonObject = new JSONObject(response);
        if(jsonObject.has(mResults)){
            array = jsonObject.getJSONArray(mResults);
            for(int i=0;i < array.length(); i++){
                mObject = array.getJSONObject(i);
                movieList.add(new Movie(mObject.getString(mPosterPath),mObject.getString(mOriginalTitle)
                        ,mObject.getString(mReleaseDate),mObject.getDouble(mUserRating),mObject.getString(mReview)));
            }
        }
        return movieList;
    }
}
