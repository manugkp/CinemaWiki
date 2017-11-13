package com.mobiledi.mgk.cinemawiki.rest;

import com.mobiledi.mgk.cinemawiki.model.CinemaResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ManuGK on 11/12/2017.
 */

public interface ApiService
{
    @GET("movie/top_rated")
    Call<CinemaResponse> getTopRatedMovies(@Query("api_key") String apiKey);
    @GET("movie/{id}")
    Call<CinemaResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

}
