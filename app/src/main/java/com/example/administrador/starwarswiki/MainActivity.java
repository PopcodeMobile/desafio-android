package com.example.administrador.starwarswiki;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import java.io.IOException;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private EndlessRecyclerViewScrollListener scrollListener;
    private RecyclerViewAdapter mAdapter;
    private SearchView searchView;
    private CharacterViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //myDataset = new ArrayList<>();
        mViewModel = ViewModelProviders.of(this).get(CharacterViewModel.class);
        final ProgressBar simpleProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        simpleProgressBar.setVisibility(View.VISIBLE);

        //checks internet connection
        if(isOnline()) {
            mViewModel.loadDatabase();
        }

        //ROOM is responsible for caching the DATA, so if there's no internet we check if there's any data in the db
        if(mViewModel.getStarWarsCharactersList() != null) {
            simpleProgressBar.setVisibility(View.INVISIBLE);
            mAdapter = new RecyclerViewAdapter(mViewModel.getStarWarsCharactersList().getValue());
            mViewModel.getStarWarsCharactersList().observe(this, new Observer<List<StarWarsCharacter>>() {
                @Override
                public void onChanged(@Nullable final List<StarWarsCharacter> starWarsCharacters) {
                    mAdapter.setStarWarsCharacters(starWarsCharacters);
                    Log.d("=>>>>>>>>>>>", "mudou");
                }
            });

            // Configure the RecyclerView
            RecyclerView recyclerViewItems = (RecyclerView) findViewById(R.id.recycler_view);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerViewItems.setLayoutManager(linearLayoutManager);
            // Retain an instance so that you can call `resetState()` for fresh searches
            scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    // Triggered only when new data needs to be appended to the list
                    Log.d("page=>>>>>>>>>>>>>>>>>>>>", String.valueOf(page));
                    mViewModel.loadNextDataFromApi(page);
                }
            };
            // Adds the scroll listener to RecyclerView
            recyclerViewItems.addOnScrollListener(scrollListener);
            recyclerViewItems.setAdapter(mAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.app_bar_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;

    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }

}

