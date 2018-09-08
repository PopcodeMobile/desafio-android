package br.com.jaysonsabino.desafioandroidpopcode;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;
import br.com.jaysonsabino.desafioandroidpopcode.ui.peoplelist.PeopleListAdapter;
import br.com.jaysonsabino.desafioandroidpopcode.ui.peoplelist.PeopleListViewModel;

public class PeopleListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private PeopleListAdapter characterListAdapter;
    private PeopleListViewModel viewModel;
    private RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = getViewModel();

        initAdapter();

        initRecyclerView();
    }

    private PeopleListViewModel getViewModel() {
        Executor executor = Executors.newFixedThreadPool(2);

        return ViewModelProviders
                .of(this, new PeopleListViewModel.Factory(getApplication(), executor))
                .get(PeopleListViewModel.class);
    }

    private void initAdapter() {
        characterListAdapter = new PeopleListAdapter(this);

        viewModel.getCharactersPagedList().observe(this, new Observer<PagedList<Character>>() {
            @Override
            public void onChanged(@Nullable PagedList<Character> characters) {
                characterListAdapter.submitList(characters);
            }
        });

        characterListAdapter.setOnItemClickListener(new PeopleListAdapter.OnClickListener() {
            @Override
            public void onClick(View v, Character character) {
                Intent intent = new Intent(PeopleListActivity.this, CharacterDetailsActivity.class);
                intent.putExtra("character", character);

                startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        list = findViewById(R.id.main_lista_personagens);

        list.setAdapter(characterListAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);

        RecyclerView.ItemAnimator animator = list.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_people_list, menu);

        MenuItem vwShowOnlyFavorites = menu.findItem(R.id.menu_peoplelist_show_only_favorites);
        MenuItem vwShowAll = menu.findItem(R.id.menu_peoplelist_show_all);

        vwShowOnlyFavorites.setVisible(!viewModel.showOnlyFavorites());
        vwShowAll.setVisible(viewModel.showOnlyFavorites());

        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_peoplelist_search).getActionView();
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_peoplelist_show_only_favorites:
                viewModel.showOnlyFavorites(true);
                invalidateOptionsMenu();
                break;
            case R.id.menu_peoplelist_show_all:
                viewModel.showOnlyFavorites(false);
                invalidateOptionsMenu();
                break;
        }

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        viewModel.search(query);
        list.scrollToPosition(0);
        return true;
    }
}
