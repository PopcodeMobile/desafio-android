package com.example.administrador.starwarswiki;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private EndlessRecyclerViewScrollListener scrollListener;
    //private List<StarWarsCharacter> myDataset;
    private RecyclerViewAdapter mAdapter;
    private CharacterViewModel characterViewModel;
    private RetrofitConfig retrofit;
    private PeopleList lastList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //myDataset = new ArrayList<>();
        CharacterViewModel mViewModel = new CharacterViewModel(getApplicationContext());
        mAdapter = new RecyclerViewAdapter(mViewModel.getStarWarsCharactersList());
        mViewModel.loadDatabase(mAdapter);
        // Configure the RecyclerView
        RecyclerView recyclerViewItems = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewItems.setLayoutManager(linearLayoutManager);
        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                Log.d("page=>>>>>>>>>>>>>>>>>>>>", String.valueOf(page));
                if(mViewModel.getLastList().getNext() != null)
                    mViewModel.loadNextDataFromApi(page, mAdapter);
            }
        };
        // Adds the scroll listener to RecyclerView
        recyclerViewItems.addOnScrollListener(scrollListener);
        recyclerViewItems.setAdapter(mAdapter);

    }


}

