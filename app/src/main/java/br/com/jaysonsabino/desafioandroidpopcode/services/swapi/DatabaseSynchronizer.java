package br.com.jaysonsabino.desafioandroidpopcode.services.swapi;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.jaysonsabino.desafioandroidpopcode.database.AppDatabase;
import br.com.jaysonsabino.desafioandroidpopcode.database.CharacterDAO;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.dto.PeopleListResponseDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * TODO Testar se esta conectado a internet antes de iniciar
 * TODO Avaliar se é melhor sincronizar a base toda de uma vez ou se é melhor sincronizar página a página
 */
public class DatabaseSynchronizer {

    private Activity activity;
    private AppDatabase database;

    public DatabaseSynchronizer(Activity activity, AppDatabase database) {
        this.activity = activity;
        this.database = database;
    }

    public void syncCharacters() {
        consultarPersonagensSWAPI(1, null);
    }

    private void consultarPersonagensSWAPI(final int page, List<Character> charactersPaginasAnteriores) {
        if (charactersPaginasAnteriores == null) {
            charactersPaginasAnteriores = new ArrayList<>();
        }

        Call<PeopleListResponseDTO> call = new ServiceFactory().getPeopleService().getList(page);
        final List<Character> characters = charactersPaginasAnteriores;
        call.enqueue(new Callback<PeopleListResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<PeopleListResponseDTO> call, @NonNull Response<PeopleListResponseDTO> response) {
                PeopleListResponseDTO dto = response.body();

                if (dto == null) {
                    Toast.makeText(activity, "Falha ao consultar lista de personagens da SWAPI.", Toast.LENGTH_LONG).show();
                    return;
                }

                characters.addAll(dto.getResults());

                boolean isLastPage = dto.getNext() == null;

                if (!isLastPage) {
                    consultarPersonagensSWAPI(page + 1, characters);
                    return;
                }

                atualizarBancoDePersonagens(characters);
            }

            @Override
            public void onFailure(@NonNull Call<PeopleListResponseDTO> call, @NonNull Throwable t) {
                Toast.makeText(activity, "Falha ao consultar lista de personagens na SWAPI.", Toast.LENGTH_LONG).show();
                Log.e("ERRO", "Erro", t);
            }
        });
    }

    private void atualizarBancoDePersonagens(List<Character> characters) {
        CharacterDAO dao = database.getCharacterDAO();

        List<Character> alreadyInsertedCharacters = dao.findAll();
        List<Character> notInsertedCharacters = new ArrayList<>();

        for (Character character : characters) {
            if (alreadyInsertedCharacters.contains(character)) {
                continue;
            }

            character.setIdByUrl();
            notInsertedCharacters.add(character);
        }

        if (notInsertedCharacters.isEmpty()) {
            Toast.makeText(activity, "Nenhum novo personagem foi encontrado na SWAPI.", Toast.LENGTH_LONG).show();
            return;
        }

        database.getCharacterDAO().insert(notInsertedCharacters);

        Toast.makeText(
                activity,
                String.format("Foram adicionados %s novos personagens ao catálogo.", notInsertedCharacters.size()),
                Toast.LENGTH_SHORT
        ).show();
    }
}
