package com.sugam.moviesapp.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieResponse {

    @SerializedName("Search")
    private List<Movie> search;

    @SerializedName("totalResults")
    private String totalResults;

    @SerializedName("Response")
    private String response;

    // Getters and Setters
    public List<Movie> getSearch() {
        return search;
    }

    public void setSearch(List<Movie> search) {
        this.search = search;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
