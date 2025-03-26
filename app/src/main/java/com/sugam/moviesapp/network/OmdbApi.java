package com.sugam.moviesapp.network;

import com.sugam.moviesapp.model.Movie;
import com.sugam.moviesapp.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface OmdbApi {
    @GET("/")
    Call<MovieResponse> searchMovies(
            @Query("apikey") String apiKey,
            @Query("s") String query
    );

    @GET("/")
    Call<Movie> getMovieDetails(
            @Query("apikey") String apiKey,
            @Query("i") String imdbId
    );
}
