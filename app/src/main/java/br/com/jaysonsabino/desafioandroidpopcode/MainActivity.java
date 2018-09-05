package br.com.jaysonsabino.desafioandroidpopcode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> itens = Arrays.asList("Teste", "Jayson");

        RecyclerView lista = findViewById(R.id.main_lista_personagens);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        lista.setLayoutManager(layoutManager);

        lista.setAdapter(new ListaPersonagensAdapter(this, itens));
    }
}
