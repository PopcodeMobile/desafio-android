package com.popcode.starwars.ui.main;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.popcode.starwars.R;
import com.popcode.starwars.data.remote.response.CharactersResponse;
import com.popcode.starwars.databinding.ActivityMainBinding;
import com.popcode.starwars.ui.detail.DetailActivity;
import com.popcode.starwars.ui.main.fragments.CharactersFragment;
import com.popcode.starwars.viewmodel.MainViewModel;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static com.popcode.starwars.util.Converters.getIdFromUrl;

public class MainActivity extends AppCompatActivity  implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private ActivityMainBinding binding;

    // adapters
    private CharacterListAdapter searchListAdapter;

    // viewModels
    private MainViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // set search adapter
        searchListAdapter = new CharacterListAdapter(characterClickCallback);
        binding.searchList.setAdapter(searchListAdapter);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        viewModel.getOnlineSearchQueryObservable().observe(this, new Observer<CharactersResponse>() {
            @Override
            public void onChanged(@Nullable CharactersResponse charactersResponse) {
                binding.setIsMainLoading(false);
                if (charactersResponse != null)
                    searchListAdapter.setCharacterList(charactersResponse.results);
            }

        });

        // Add project list fragment if this is first creation
        if (savedInstanceState == null) {
            CharactersFragment fragment = new CharactersFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, CharactersFragment.TAG).commit();
        }
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
                binding.setIsSearching(true);
                viewModel.setQuery(query);
                binding.setIsMainLoading(true);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                searchListAdapter.clear();
                binding.setIsSearching(true);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                binding.setIsSearching(false);
                return true;
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


    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
