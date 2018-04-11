package com.example.anatoly.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.anatoly.popularmovies.Utils.JSONUtils;
import com.example.anatoly.popularmovies.Utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, MoviesRecyclerAdapter.OnItemClickListener{

    RecyclerView moviesPicsRV;
    ProgressBar loadingMoviesPB;
    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesPicsRV = findViewById(R.id.rv_movies_pics);
        loadingMoviesPB = findViewById(R.id.pb_movies_loading);

        moviesPicsRV.setHasFixedSize(true);
        GridLayoutManager gridManager = new GridLayoutManager(this, 2);
        moviesPicsRV.setLayoutManager(gridManager);
    }

    /**
     * Solution of adding Spinner to the menu got here:
     * https://stackoverflow.com/questions/37250397/how-to-add-a-spinner-next-to-a-menu-in-the-toolbar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.menu_spinner);
        Spinner spinner = (Spinner) item.getActionView();
        String[] orderTypes = getResources().getStringArray(R.array.order_types);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,orderTypes);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(0);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(NetworkUtils.isOnline(this)) {
            new HTTPLoadTask().execute(NetworkUtils.buildMoviesUrl(position == 0 ? NetworkUtils.SORT_ORDER.top_rated : NetworkUtils.SORT_ORDER.popular));
        }
        else{
            Toast.makeText(this, "Please check the Internet connection!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //Do nothing
    }

    private void showProgress(){
        loadingMoviesPB.setVisibility(View.VISIBLE);
        moviesPicsRV.setVisibility(View.INVISIBLE);
    }

    private void hideProgress(){
        loadingMoviesPB.setVisibility(View.INVISIBLE);
        moviesPicsRV.setVisibility(View.VISIBLE);
    }


    @Override
    public void itemClicked(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Movie.TAG, movies.get(position));
        startActivity(intent);
    }

    class HTTPLoadTask extends AsyncTask<URL, Void, String>{
        @Override
        protected void onPreExecute() {
            showProgress();
        }

        @Override
        protected String doInBackground(URL... urls) {
            String gotString = "";
            try {
                gotString = NetworkUtils.getResponseFromHttpUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return gotString;
        }

        @Override
        protected void onPostExecute(String s) {
            hideProgress();
            if(s==null || s.equals("")){return;}
            movies = new ArrayList<>();
            JSONArray array = JSONUtils.parseResults(s);
            if(array == null || array.length()==0) {return;}
            for (int i = 0; i < array.length(); i++) {
                try {
                    JSONObject jsonMovie = array.getJSONObject(i);
                    movies.add(JSONUtils.parseMovie(jsonMovie));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            MoviesRecyclerAdapter adapter = new MoviesRecyclerAdapter(movies, MainActivity.this);
            moviesPicsRV.setAdapter(adapter);
        }
    }
}
