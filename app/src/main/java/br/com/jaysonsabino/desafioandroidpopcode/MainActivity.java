package br.com.jaysonsabino.desafioandroidpopcode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.jaysonsabino.desafioandroidpopcode.adapters.CharacterListAdapter;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Character> characters = new ArrayList<>();
        characters.add(new Character("Jayson", "Male"));
        characters.add(new Character("Luke"));

        RecyclerView lista = findViewById(R.id.main_lista_personagens);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        lista.setLayoutManager(layoutManager);

        lista.setAdapter(new CharacterListAdapter(this, characters));
    }
}
