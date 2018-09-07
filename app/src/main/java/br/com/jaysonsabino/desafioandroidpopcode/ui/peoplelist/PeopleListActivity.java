package br.com.jaysonsabino.desafioandroidpopcode.ui.peoplelist;

import android.arch.lifecycle.Observer;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import br.com.jaysonsabino.desafioandroidpopcode.R;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;
import br.com.jaysonsabino.desafioandroidpopcode.ui.characterdetails.CharacterDetailsActivity;

public class PeopleListActivity extends AppCompatActivity {

    private PeopleListAdapter characterListAdapter;
    private PeopleListViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Executor executor = Executors.newFixedThreadPool(2);

        viewModel = new PeopleListViewModel(this, executor);

        viewModel.deleteCharactersLocalCacheIfConnected();

        initAdapter();

        initRecyclerView();
    }

    private void initAdapter() {
        characterListAdapter = new PeopleListAdapter(this);

        viewModel.getCharacters().observe(this, new Observer<PagedList<Character>>() {
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
        RecyclerView charactersRecyclerView = findViewById(R.id.main_lista_personagens);

        charactersRecyclerView.setAdapter(characterListAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        charactersRecyclerView.setLayoutManager(layoutManager);

        RecyclerView.ItemAnimator animator = charactersRecyclerView.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
    }
}