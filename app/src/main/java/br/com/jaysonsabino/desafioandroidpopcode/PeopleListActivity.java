package br.com.jaysonsabino.desafioandroidpopcode;

import android.arch.lifecycle.Observer;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import br.com.jaysonsabino.desafioandroidpopcode.adapters.CharacterListAdapter;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;
import br.com.jaysonsabino.desafioandroidpopcode.viewmodels.PeopleListViewModel;

public class PeopleListActivity extends AppCompatActivity {

    private CharacterListAdapter characterListAdapter;
    private PeopleListViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new PeopleListViewModel(this);

        configurarLista();

        viewModel.sincronizarBancoDePersonagens();
    }

    private void configurarLista() {
        RecyclerView charactersRecyclerView = findViewById(R.id.main_lista_personagens);

        characterListAdapter = new CharacterListAdapter(this, new DiffUtil.ItemCallback<Character>() {
            @Override
            public boolean areItemsTheSame(@NonNull Character character, @NonNull Character t1) {
                return character.getId() == t1.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Character character, @NonNull Character t1) {
                return character == t1;
            }
        });

        viewModel.getCharacters().observe(this, new Observer<PagedList<Character>>() {
            @Override
            public void onChanged(@Nullable PagedList<Character> characters) {
                characterListAdapter.submitList(characters);
            }
        });

        charactersRecyclerView.setAdapter(characterListAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        charactersRecyclerView.setLayoutManager(layoutManager);
    }
}
