package com.mobiledi.mgk.cinemawiki;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mobiledi.mgk.cinemawiki.adapter.CinemaAdapter;
import com.mobiledi.mgk.cinemawiki.model.Cinema;
import com.mobiledi.mgk.cinemawiki.model.CinemaResponse;
import com.mobiledi.mgk.cinemawiki.rest.ApiClient;
import com.mobiledi.mgk.cinemawiki.rest.ApiService;
import com.mobiledi.mgk.cinemawiki.rest.ClickListener;
import com.mobiledi.mgk.cinemawiki.view.DividerItemDecoration;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity  {

    private static final String TAG =HomeActivity.class.getSimpleName();
    private final static String API_KEY ="7958f47b2f5e3bf1d0b16f0432cedc38"; //TMDb Api Key
    List<Cinema> cinema=new ArrayList<>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final RecyclerView recyclerView =(RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<CinemaResponse> call=apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<CinemaResponse>() {
            @Override
            public void onResponse(Call<CinemaResponse> call, Response<CinemaResponse> response)
            {
                int statusCode=response.code();
                cinema=response.body().getResults();
                recyclerView.setAdapter(new CinemaAdapter(cinema,R.layout.listcinema,getApplicationContext()));
                Log.d(TAG,"Total movies:"+cinema.size());//cinema list size Log
            }

            @Override
            public void onFailure(Call<CinemaResponse> call, Throwable t)
            {
                Log.e(TAG,t.toString()); //Error Log
            }

        });
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                String title=cinema.get(position).getTitle().toString();
                String releaseYear=cinema.get(position).getReleaseDate().toString();
                String description=cinema.get(position).getOverview().toString();
                String posterPath="http://image.tmdb.org/t/p/w185/"+cinema.get(position).getPosterPath();
                String rating=cinema.get(position).getVoteAverage().toString();
//                Toast.makeText(HomeActivity.this, "Single Click on position        :"+ posterPath,
//                       Toast.LENGTH_SHORT).show();
                Intent detailview= new Intent(getApplicationContext(),DetailViewActivity.class);
                detailview.putExtra("title",title);
                detailview.putExtra("releaseYear",releaseYear);
                detailview.putExtra("posterPath",posterPath);
                detailview.putExtra("description",description);
                detailview.putExtra("rating",rating);
                startActivity(detailview);
            }

            @Override
            public void onLongClick(View view, int position) {
                String title=cinema.get(position).getTitle().toString();
                Toast.makeText(HomeActivity.this, title,
                        Toast.LENGTH_LONG).show();
            }
        }));


    }
    public void onBackPressed() {
        Toast.makeText(HomeActivity.this, "Cinema Wiki ",
                Toast.LENGTH_LONG).show();//disable backbutton
    }



}
