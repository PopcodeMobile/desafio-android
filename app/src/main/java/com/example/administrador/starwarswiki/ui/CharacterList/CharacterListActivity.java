package com.example.administrador.starwarswiki.ui.CharacterList;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.administrador.starwarswiki.R;
import com.example.administrador.starwarswiki.data.model.StarWarsCharacter;

import java.util.List;


public class CharacterListActivity extends AppCompatActivity {
    private EndlessRecyclerViewScrollListener scrollListener;
    private RecyclerViewAdapter mAdapter;
    private SearchView searchView;
    private CharacterListViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);

        //ROOM is responsible for caching the DATA, so if there's no internet we check if there's any data in the db
        //this step is applyied when the viewModel is instatiated
        mViewModel = ViewModelProviders.of(this).get(CharacterListViewModel.class);
        View view = findViewById(R.id.activity_main);

        mViewModel.loadDatabase();
        mViewModel.dispatchPendingFavorites();

        mViewModel.getFavoriteResponseMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Snackbar snackbar = Snackbar.make(view, s, Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        mAdapter = new RecyclerViewAdapter(mViewModel);
        mViewModel.getStarWarsCharactersList().observe(this, new Observer<List<StarWarsCharacter>>() {
            @Override
            public void onChanged(@Nullable final List<StarWarsCharacter> starWarsCharacters) {
                mAdapter.setStarWarsCharacters(starWarsCharacters);
                int tmp = mViewModel.getStarWarsCharactersList().getValue().size()/10;
                if (tmp == 0) {
                    scrollListener.setCurrentPage(1);
                }else{
                    scrollListener.setCurrentPage(tmp);
                }
                Log.d("=>>>>>>>>>>>", "characterlist updated, new page number: "+ tmp );
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
        searchView.setQueryHint(getString(R.string.search_hint));

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

}

