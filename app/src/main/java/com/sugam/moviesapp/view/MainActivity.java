package com.sugam.moviesapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sugam.moviesapp.R;
import com.sugam.moviesapp.adapter.MovieAdapter;
import com.sugam.moviesapp.model.Movie;
import com.sugam.moviesapp.viewmodel.MovieViewModel;

import java.util.List;
public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieClickListener {

    private EditText searchEditText;
    private Button searchButton;
    private RecyclerView movieRecyclerView;
    private ProgressBar loadingIndicator;
    private MovieViewModel movieViewModel;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        movieRecyclerView = findViewById(R.id.movieRecyclerView);
        loadingIndicator = findViewById(R.id.loadingIndicator);

        // Set up RecyclerView
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieAdapter = new MovieAdapter(this);
        movieRecyclerView.setAdapter(movieAdapter);

        // Initialize ViewModel
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        // Observe changes to movie list
        movieViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                loadingIndicator.setVisibility(View.GONE);
                if (movies != null && !movies.isEmpty()) {
                    movieAdapter.setMovies(movies);
                } else {
                    Toast.makeText(MainActivity.this, "No movies found!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Observe error messages
        movieViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                loadingIndicator.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        // Search button click listener
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchEditText.getText().toString().trim();
                if (!query.isEmpty()) {
                    loadingIndicator.setVisibility(View.VISIBLE);
                    movieViewModel.searchMovies(query);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a search term.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onMovieClick(Movie movie) {
        Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
        intent.putExtra("MOVIE_ID", movie.getImdbID());
        startActivity(intent);
    }
}
