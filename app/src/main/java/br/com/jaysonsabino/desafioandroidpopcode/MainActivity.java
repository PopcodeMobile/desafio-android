package br.com.jaysonsabino.desafioandroidpopcode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import br.com.jaysonsabino.desafioandroidpopcode.adapters.CharacterListAdapter;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.ServiceFactory;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.dto.PeopleListResponseDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configurarLista();

        consultarPersonagensSWAPI();
    }

    private void consultarPersonagensSWAPI() {
        Call<PeopleListResponseDTO> list = new ServiceFactory().getPeopleService().getList();
        list.enqueue(new Callback<PeopleListResponseDTO>() {
            @Override
            public void onResponse(Call<PeopleListResponseDTO> call, Response<PeopleListResponseDTO> response) {
                PeopleListResponseDTO dto = response.body();

                if (dto == null) {
                    Toast.makeText(MainActivity.this, "Falha ao consultar lista de personagens.", Toast.LENGTH_LONG).show();
                    return;
                }

                popularLista(dto.getResults());
            }

            @Override
            public void onFailure(Call<PeopleListResponseDTO> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Falha ao consultar lista de personagens.", Toast.LENGTH_LONG).show();
                Log.e("ERRO", "Erro", t);
            }
        });
    }

    private void configurarLista() {
        lista = findViewById(R.id.main_lista_personagens);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        lista.setLayoutManager(layoutManager);
    }

    private void popularLista(List<Character> characters) {
        lista.setAdapter(new CharacterListAdapter(this, characters));
    }
}
