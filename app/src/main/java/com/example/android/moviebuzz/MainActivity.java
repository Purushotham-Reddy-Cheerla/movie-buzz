package com.example.android.moviebuzz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.moviebuzz.fragments.MovieFragment;

public class MainActivity extends AppCompatActivity {
    private static int mFilter;
    private TextView tCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tCategory = findViewById(R.id.category);
        if(savedInstanceState !=null){
            mFilter = savedInstanceState.getInt("filter");
            showMovies(mFilter);
        }else {
            showMovies(R.id.action_popular);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("filter", mFilter);
        super.onSaveInstanceState(outState);
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
                showMovies(R.id.action_popular);
                break;
            case R.id.action_top_rated:
                showMovies(R.id.action_top_rated);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMovies(int filter) {
        switch (filter){
            case R.id.action_popular: tCategory.setText(getString(R.string.popular_movies));
            mFilter = R.string.popular_filter;
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container
                    ,MovieFragment.newInstance(getString(R.string.popular_filter))
            ).commit();
            break;
            case R.id.action_top_rated: tCategory.setText(getString(R.string.top_rated));
            mFilter = R.string.top_rated_filter;
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container
                    ,MovieFragment.newInstance(getString(R.string.top_rated_filter))
            ).commit();
        }
    }
}
