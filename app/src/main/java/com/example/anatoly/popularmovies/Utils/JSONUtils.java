package com.example.anatoly.popularmovies.Utils;

import com.example.anatoly.popularmovies.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONUtils {


    private final static String JSON_KEY_RESULTS = "results";


    private final static String JSON_KEY_POSTER_PATH = "poster_path";
    private final static String JSON_KEY_ADULT = "adult";
    private final static String JSON_KEY_OVERVIEW = "overview";
    private final static String JSON_KEY_RELEASE_DATE = "release_date";
    private final static String JSON_KEY_GENRE_IDS = "genre_ids";
    private final static String JSON_KEY_ID = "id";
    private final static String JSON_KEY_ORIGINAL_TITLE = "original_title";
    private final static String JSON_KEY_ORIGINAL_LANG = "original_language";
    private final static String JSON_KEY_TITLE = "title";
    private final static String JSON_KEY_BACKDROP_PATH = "backdrop_path";
    private final static String JSON_KEY_POPULARITY = "popularity";
    private final static String JSON_KEY_VOTE_COUNT= "vote_count";
    private final static String JSON_KEY_VIDEO= "video";
    private final static String JSON_KEY_VOTE_AVR= "vote_average";


    /**
     * Function for parsing the whole string that came as HTTP response
     * @return JSONArray of movies-JSONObject's
     */
    public static JSONArray parseResults (String responseString){
        JSONArray array = null;
        try {
            JSONObject resultJSON = new JSONObject(responseString);
            array = resultJSON.getJSONArray(JSON_KEY_RESULTS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }


    /**
     * Function for getting Movie object from JSONObject
     * @return Movie object with all fields from jsonMovie
     */
    public static Movie parseMovie (JSONObject jsonMovie){
        Movie movie = new Movie();

        String posterPath = jsonMovie.optString(JSON_KEY_POSTER_PATH);
        boolean adult = jsonMovie.optBoolean(JSON_KEY_ADULT);
        String overview = jsonMovie.optString(JSON_KEY_OVERVIEW);
        String releaseDate = jsonMovie.optString(JSON_KEY_RELEASE_DATE);
        List<Integer> genreIDs = new ArrayList<>();
        try {
            genreIDs = getListFromJSONArray(jsonMovie.getJSONArray(JSON_KEY_GENRE_IDS));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int id = jsonMovie.optInt(JSON_KEY_ID);
        String originalTitle = jsonMovie.optString(JSON_KEY_ORIGINAL_TITLE);
        String originalLang = jsonMovie.optString(JSON_KEY_ORIGINAL_LANG);
        String title = jsonMovie.optString(JSON_KEY_TITLE);
        String backdropPath = jsonMovie.optString(JSON_KEY_BACKDROP_PATH);
        double popularity = jsonMovie.optDouble(JSON_KEY_POPULARITY);
        int voteCount = jsonMovie.optInt(JSON_KEY_VOTE_COUNT);
        boolean video = jsonMovie.optBoolean(JSON_KEY_VIDEO);
        double voteAvr = jsonMovie.optDouble(JSON_KEY_VOTE_AVR);

        movie.setPosterPath(posterPath);
        movie.setAdult(adult);
        movie.setOverview(overview);
        movie.setReleaseDate(releaseDate);
        movie.setGenreIDs(genreIDs);
        movie.setId(id);
        movie.setOriginalTitle(originalTitle);
        movie.setOriginalLang(originalLang);
        movie.setTitle(title);
        movie.setBackdropPath(backdropPath);
        movie.setPopularity(popularity);
        movie.setVoteCount(voteCount);
        movie.setVideo(video);
        movie.setVoteAvr(voteAvr);

        return movie;
    }


    /**
     * Function that makes a List of Integers from JSONArray
     * @param array - JSONArray that looks like: [1, 2, 3]
     * @return List of Integers or just empty list with size=0
     */
    private static List<Integer> getListFromJSONArray(JSONArray array){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                list.add(array.getInt(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

}
