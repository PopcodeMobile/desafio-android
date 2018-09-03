package com.popcode.starwars.ui.main;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.popcode.starwars.R;
import com.popcode.starwars.data.local.entity.CharacterElement;
import com.popcode.starwars.data.remote.response.CharactersResponse;
import com.popcode.starwars.databinding.ActivityMainBinding;
import com.popcode.starwars.ui.common.listeners.EndlessRecyclerViewScrollListener;
import com.popcode.starwars.ui.detail.DetailActivity;
import com.popcode.starwars.viewmodel.CharacterListViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class MainActivity extends AppCompatActivity  implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private CharacterListAdapter characterListAdapter;
    private CharacterListAdapter searchListAdapter;
    private ActivityMainBinding binding;
    private EndlessRecyclerViewScrollListener scrollListener;
    private CharacterListViewModel viewModel;
    private Integer pageCount = 1;
    private Boolean canShowLoading = true;
    private Boolean offline = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        characterListAdapter = new CharacterListAdapter(characterClickCallback);
        searchListAdapter = new CharacterListAdapter(characterClickCallback);
        binding.characterList.setAdapter(characterListAdapter);
        binding.characterSearchList.setAdapter(searchListAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.characterList.setLayoutManager(linearLayoutManager);
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (canShowLoading)
                    binding.setIsLoading(true);
            }
        };

        binding.setIsMainLoading(true);

        binding.setSearching(false);

        binding.characterList.addOnScrollListener(scrollListener);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CharacterListViewModel.class);

        viewModel.getCharactersOfflineObservable().observe(this, new Observer<List<CharacterElement>>() {
            @Override
            public void onChanged(@Nullable List<CharacterElement> characters) {
                if (characters != null) {
                    binding.setIsLoading(false);
                    binding.setIsMainLoading(false);
                    if (offline){
                        if (characters.size() == 0){
                            Snackbar.make(findViewById(R.id.main_layout), "Connect to the internet to get characters.", Snackbar.LENGTH_LONG).show();
                        }
                        characterListAdapter.setCharacterList(characters);
                    }
                }
            }
        });

        viewModel.setPage(pageCount);

        viewModel.getCharactersResponseObservable().observe(this, new Observer<CharactersResponse>() {
            @Override
            public void onChanged(@Nullable CharactersResponse charactersResponse) {
                binding.setIsLoading(false);
                binding.setIsMainLoading(false);
                if (charactersResponse != null) {
                    viewModel.setElements(charactersResponse.results);
                    characterListAdapter.addCharactersList(charactersResponse.results);
                    if (charactersResponse.next != null){
                        pageCount += 1;
                        viewModel.setPage(pageCount);
                    }
                    else {
                        canShowLoading = false;
                    }
                } else {
                    offline = true;
                    viewModel.setElements(null);
                }
            }

        });

        viewModel.getCharacterSearchObservable().observe(this, new Observer<CharactersResponse>() {
            @Override
            public void onChanged(@Nullable CharactersResponse charactersResponse) {
                binding.setIsLoading(false);
                binding.setIsMainLoading(false);
                if (charactersResponse != null) {
                    searchListAdapter.addCharactersList(charactersResponse.results);
                }
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.searchBar);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Procurar personagens");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.setQuery(query);
                binding.setIsMainLoading(true);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    searchListAdapter.clear();
                    binding.setSearching(false);
                } else {
                    binding.setSearching(true);
                }
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.searchBar) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private final CharacterClickCallback characterClickCallback = character -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            intent.putExtra("PEOPLE_ID", getIdFromUrl(character.url));
            intent.putExtra("PEOPLE_NAME", character.name);
            intent.putExtra("PEOPLE_PLANET", getIdFromUrl(character.homeworld));
            if (character.species == null){
                if (character.specie != null)
                    intent.putExtra("PEOPLE_SPECIE", getIdFromUrl(character.specie));
            } else {
                intent.putExtra("PEOPLE_SPECIE", getIdFromUrl(character.species.get(0)));
            }
            startActivity(intent);
        }
    };

    private Integer getIdFromUrl(String url) {
        String[] splitted = url.split("/");
        return Integer.valueOf(splitted[splitted.length - 1]);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
