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
    private List<StarWarsCharacter> myDataset;
    private RecyclerViewAdapter mAdapter;
    private CharacterViewModel characterViewModel;
    private RetrofitConfig retrofit;
    private PeopleList lastList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDataset = new ArrayList<>();
        loadDatabase();
        // Configure the RecyclerView
        RecyclerView rvItems = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvItems.setLayoutManager(linearLayoutManager);
        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                Log.d("page=>>>>>>>>>>>>>>>>>>>>", String.valueOf(page));
                if(lastList.getNext() != null)
                    loadNextDataFromApi(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvItems.addOnScrollListener(scrollListener);
        mAdapter = new RecyclerViewAdapter(myDataset);
        rvItems.setAdapter(mAdapter);

    }

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        CharacterRepository characterRepository = new CharacterRepository(getApplicationContext());

        Call<PeopleList> call = retrofit.getService().getStarWarsCharacters(offset);
        call.enqueue(new Callback<PeopleList>() {
            @Override
            public void onResponse(Call<PeopleList> call, Response<PeopleList> response) {
               lastList = response.body();
               for (StarWarsCharacter starWarsCharacter : response.body().getResults()) {
                    characterRepository.insertCharacter(starWarsCharacter);
                    myDataset.add(starWarsCharacter);
                    //textViewName.setText(starWarsCharacter.getName());
                }
               mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<PeopleList> call, Throwable t) {
                Log.d("ERROR", "deu erro",t);
            }
        });

            //  --> Deserialize and construct new model objects from the API response
            //  --> Append the new data objects to the existing set of items inside the array of items
            //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
    }

    public void loadDatabase(){

        Log.d("aqui","auiiiii1");
        CharacterRepository characterRepository = new CharacterRepository(getApplicationContext());
        characterViewModel = new CharacterViewModel(characterRepository);
        retrofit = new RetrofitConfig();
        Call<PeopleList> call = retrofit.getService().getStarWarsCharacters();
        call.enqueue(new Callback<PeopleList>() {
            @Override
            public void onResponse(Call<PeopleList> call, Response<PeopleList> response) {
                lastList = response.body();
                for (StarWarsCharacter starWarsCharacter : response.body().getResults()) {
                    characterRepository.insertCharacter(starWarsCharacter);
                    myDataset.add(starWarsCharacter);
                    //textViewName.setText(starWarsCharacter.getName());
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PeopleList> call, Throwable t) {
                Log.d("ERROR", "deu erro",t);
            }
        });
    }
}

