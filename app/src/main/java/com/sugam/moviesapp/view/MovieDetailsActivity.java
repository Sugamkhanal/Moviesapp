package com.sugam.moviesapp.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.sugam.moviesapp.R;
import com.sugam.moviesapp.model.Movie;
import com.sugam.moviesapp.viewmodel.MovieViewModel;

public class MovieDetailsActivity  extends AppCompatActivity {

    private ImageView posterImageView;
    private TextView titleTextView, yearTextView, directorTextView, actorsTextView, plotTextView, genreTextView, ratingTextView;
    private ProgressBar loadingIndicator;
    private MovieViewModel movieViewModel;
    private String movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);


        posterImageView = findViewById(R.id.posterImageView);
        titleTextView = findViewById(R.id.titleTextView);
        yearTextView = findViewById(R.id.yearTextView);
        directorTextView = findViewById(R.id.directorTextView);
        actorsTextView = findViewById(R.id.actorsTextView);
        plotTextView = findViewById(R.id.plotTextView);
        genreTextView = findViewById(R.id.genreTextView);
        ratingTextView = findViewById(R.id.ratingTextView);
        loadingIndicator = findViewById(R.id.loadingIndicator);


        movieId = getIntent().getStringExtra("MOVIE_ID");
        if (movieId == null || movieId.isEmpty()) {
            Toast.makeText(this, "No movie ID provided!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);


        movieViewModel.getMovieDetails().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                loadingIndicator.setVisibility(View.GONE);
                if (movie != null) {
                    displayMovieDetails(movie);
                } else {
                    Toast.makeText(MovieDetailsActivity.this, "Failed to load movie details", Toast.LENGTH_SHORT).show();
                }
            }
        });


        movieViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                loadingIndicator.setVisibility(View.GONE);
                Toast.makeText(MovieDetailsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });


        loadingIndicator.setVisibility(View.VISIBLE);
        movieViewModel.fetchMovieDetails(movieId);
    }


    private void displayMovieDetails(Movie movie) {
        titleTextView.setText(movie.getTitle());
        yearTextView.setText("Year: " + movie.getYear());
        directorTextView.setText("Director: " + movie.getDirector());
        actorsTextView.setText("Actors: " + movie.getActors());
        plotTextView.setText(movie.getPlot());
        genreTextView.setText("Genre: " + movie.getGenre());
        ratingTextView.setText("IMDb Rating: " + movie.getImdbRating());


        Glide.with(this)
                .load(movie.getPoster())
                .placeholder(R.drawable.placeholder)
                .into(posterImageView);
    }
}
