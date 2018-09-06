package br.com.jaysonsabino.desafioandroidpopcode.viewmodels;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import br.com.jaysonsabino.desafioandroidpopcode.adapters.CharacterListAdapter;
import br.com.jaysonsabino.desafioandroidpopcode.database.AppDatabase;
import br.com.jaysonsabino.desafioandroidpopcode.database.DatabaseFactory;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.ServiceFactory;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.dto.PeopleListResponseDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeopleListViewModel {

    private Activity activity;
    private CharacterListAdapter characterListAdapter;
    private final AppDatabase appDatabase;

    public PeopleListViewModel(Activity activity, CharacterListAdapter characterListAdapter) {
        this.activity = activity;
        this.characterListAdapter = characterListAdapter;
        appDatabase = new DatabaseFactory().getDatabase(activity);
    }

    public void consultarPersonagensDatabase() {
        List<Character> characters = appDatabase.getCharacterDAO().findAll();

        characterListAdapter.getCharacters().addAll(characters);
        characterListAdapter.notifyDataSetChanged();

        Toast.makeText(activity, "Lista de personagens do banco.", Toast.LENGTH_LONG).show();
    }

    public void consultarPersonagensSWAPI(final int page) {
        Call<PeopleListResponseDTO> list = new ServiceFactory().getPeopleService().getList(page);
        list.enqueue(new Callback<PeopleListResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<PeopleListResponseDTO> call, @NonNull Response<PeopleListResponseDTO> response) {
                PeopleListResponseDTO dto = response.body();

                if (dto == null) {
                    Toast.makeText(activity, "Falha ao consultar lista de personagens.", Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(activity, "Consulta de personagens finalizada com sucesso. Página: " + page, Toast.LENGTH_SHORT).show();

                List<Character> characters = dto.getResults();

                characterListAdapter.getCharacters().addAll(characters);
                characterListAdapter.notifyDataSetChanged();


                for (Character character : characters) {
                    character.setIdByUrl();
                    appDatabase.getCharacterDAO().insert(character);
                }

                if (dto.getNext() != null) {
                    consultarPersonagensSWAPI(page + 1);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PeopleListResponseDTO> call, @NonNull Throwable t) {
                Toast.makeText(activity, "Falha ao consultar lista de personagens.", Toast.LENGTH_LONG).show();
                Log.e("ERRO", "Erro", t);
            }
        });
    }
}
