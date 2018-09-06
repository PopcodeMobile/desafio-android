package br.com.jaysonsabino.desafioandroidpopcode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.jaysonsabino.desafioandroidpopcode.adapters.CharacterListAdapter;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.ServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carregarLista();

        Call<Character> test = new ServiceFactory().getPeopleService().getTest();

        Log.i("DEBUGANDO", "INICIO DA REQUISICAO");
        test.enqueue(new Callback<Character>() {
            @Override
            public void onResponse(Call<Character> call, Response<Character> response) {
                Log.i("sucesso", "show de bola");
            }

            @Override
            public void onFailure(Call<Character> call, Throwable t) {
                Log.e("falha", "deu ruim", t);
            }
        });
    }

    private void carregarLista() {
        List<Character> characters = new ArrayList<>();
        characters.add(new Character("Jayson", "Male", "1.71m", "73kg"));
        characters.add(new Character("Luke", "Male", "1.65m", "70kg"));

        RecyclerView lista = findViewById(R.id.main_lista_personagens);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        lista.setLayoutManager(layoutManager);

        lista.setAdapter(new CharacterListAdapter(this, characters));
    }
}
