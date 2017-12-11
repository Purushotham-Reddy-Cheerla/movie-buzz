package com.example.android.moviebuzz.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by purus on 11/24/2017.
 */

@SuppressWarnings("ALL")
public class Movie implements Parcelable{

    public final String posterPath;
    public final String review;
    public final String originalTitle;
    public final double userRating;
    public final String releaseDate;

    public Movie(String path, String title, String date, double rating, String overview){
        this.posterPath = path;
        this.originalTitle = title;
        this.releaseDate = date;
        this.userRating = rating;
        this.review = overview;
    }

    private Movie(Parcel in) {
        posterPath = in.readString();
        originalTitle = in.readString();
        releaseDate = in.readString();
        userRating = in.readDouble();
        review = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterPath);
        dest.writeString(originalTitle);
        dest.writeString(releaseDate);
        dest.writeDouble(userRating);
        dest.writeString(review);
    }

}
