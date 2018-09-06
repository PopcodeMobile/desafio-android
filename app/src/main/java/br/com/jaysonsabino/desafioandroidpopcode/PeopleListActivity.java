package br.com.jaysonsabino.desafioandroidpopcode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import br.com.jaysonsabino.desafioandroidpopcode.adapters.CharacterListAdapter;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;
import br.com.jaysonsabino.desafioandroidpopcode.viewmodels.PeopleListViewModel;

public class PeopleListActivity extends AppCompatActivity {

    private CharacterListAdapter characterListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configurarLista();

        new PeopleListViewModel(this, characterListAdapter).consultarPersonagensSWAPI(1);
    }

    private void configurarLista() {
        characterListAdapter = new CharacterListAdapter(this, new ArrayList<Character>());

        RecyclerView charactersRecyclerView = findViewById(R.id.main_lista_personagens);
        charactersRecyclerView.setAdapter(characterListAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        charactersRecyclerView.setLayoutManager(layoutManager);
    }
}
