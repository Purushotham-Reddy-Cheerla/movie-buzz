package com.example.android.moviebuzz.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;

import com.example.android.moviebuzz.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import okhttp3.OkHttpClient;

/**
 * Created by purushotham on 11/24/2017.
 */

public final class NetworkUtils {

    private static final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/";

    private static final String api_key = "api_key";
    private static final String language = "language";
    private static final String page = "page";
    private static final String mLanguage = "en-US";

    public static final String IMAGE_URL = "http://image.tmdb.org/t/p/w500";

    public static URL buildUrl(String queryPath, int pageNum) {
        Uri uri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(queryPath)
                .appendQueryParameter(language, mLanguage)
                .appendQueryParameter(page, String.valueOf(pageNum))
                .appendQueryParameter(api_key, BuildConfig.MDB_API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getMovieData(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = connection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");
            boolean hasContent = scanner.hasNext();
            if (hasContent) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            connection.disconnect();
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        if (connectivityManager != null) {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            return !((info == null) || (info.getType() != ConnectivityManager.TYPE_MOBILE && info.getType() != ConnectivityManager.TYPE_WIFI)
                    || !(info.isConnected()));
        } else {
            return false;
        }

    }

}
