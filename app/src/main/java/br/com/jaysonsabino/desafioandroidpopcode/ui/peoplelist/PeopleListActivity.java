package br.com.jaysonsabino.desafioandroidpopcode.ui.peoplelist;

import android.arch.lifecycle.Observer;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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

        viewModel = new PeopleListViewModel(this);

        viewModel.apagarPersonagensBancoLocal();

        configurarAdapter();

        configurarLista();
    }

    private void configurarAdapter() {
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

    private void configurarLista() {
        RecyclerView charactersRecyclerView = findViewById(R.id.main_lista_personagens);

        charactersRecyclerView.setAdapter(characterListAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        charactersRecyclerView.setLayoutManager(layoutManager);
    }
}
