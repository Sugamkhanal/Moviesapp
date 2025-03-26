package com.sugam.moviesapp.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sugam.moviesapp.model.Movie;
import com.sugam.moviesapp.model.MovieResponse;
import com.sugam.moviesapp.network.OmdbApi;
import com.sugam.moviesapp.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class MovieViewModel extends ViewModel {

    private final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private final MutableLiveData<Movie> movieDetails = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    private final OmdbApi omdbApi;

    public MovieViewModel() {
        // Initialize Retrofit API instance
        omdbApi = RetrofitClient.getInstance().create(OmdbApi.class);
    }


    public void searchMovies(String query) {
        omdbApi.searchMovies("1162b89d", query).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getSearch() != null) {
                    movies.setValue(response.body().getSearch());
                } else {
                    error.setValue("No movies found.");
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                error.setValue("Failed to fetch, try again.");
            }
        });
    }


    public void fetchMovieDetails(String imdbId) {
        omdbApi.getMovieDetails("1162b89d", imdbId).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful() && response.body() != null) {
                    movieDetails.setValue(response.body());
                } else {
                    error.setValue("Failed loading movie details.");
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                error.setValue("Failed to fetch movie details, try again.");
            }
        });
    }


    public LiveData<List<Movie>> getMovies() {
        return movies;
    }


    public LiveData<Movie> getMovieDetails() {
        return movieDetails;
    }


    public LiveData<String> getError() {
        return error;
    }
}
