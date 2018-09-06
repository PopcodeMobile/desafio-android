package br.com.jaysonsabino.desafioandroidpopcode;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.jaysonsabino.desafioandroidpopcode.adapters.CharacterListAdapter;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.ServiceFactory;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.dto.PeopleListResponseDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final List<Character> characters = new ArrayList<>();
    private CharacterListAdapter characterListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configurarLista();

        consultarPersonagensSWAPI(1);
    }

    private void consultarPersonagensSWAPI(final int page) {
        Call<PeopleListResponseDTO> list = new ServiceFactory().getPeopleService().getList(page);
        list.enqueue(new Callback<PeopleListResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<PeopleListResponseDTO> call, @NonNull Response<PeopleListResponseDTO> response) {
                PeopleListResponseDTO dto = response.body();

                if (dto == null) {
                    Toast.makeText(MainActivity.this, "Falha ao consultar lista de personagens.", Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(MainActivity.this, "Consulta de personagens finalizada com sucesso. Página: " + page, Toast.LENGTH_SHORT).show();

                characters.addAll(dto.getResults());
                characterListAdapter.notifyDataSetChanged();

                if (dto.getNext() != null) {
                    consultarPersonagensSWAPI(page + 1);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PeopleListResponseDTO> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Falha ao consultar lista de personagens.", Toast.LENGTH_LONG).show();
                Log.e("ERRO", "Erro", t);
            }
        });
    }

    private void configurarLista() {
        characterListAdapter = new CharacterListAdapter(this, characters);

        RecyclerView charactersRecyclerView = findViewById(R.id.main_lista_personagens);
        charactersRecyclerView.setAdapter(characterListAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        charactersRecyclerView.setLayoutManager(layoutManager);
    }
}
