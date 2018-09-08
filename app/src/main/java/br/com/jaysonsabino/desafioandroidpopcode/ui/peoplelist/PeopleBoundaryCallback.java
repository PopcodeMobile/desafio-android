package br.com.jaysonsabino.desafioandroidpopcode.ui.peoplelist;

import android.app.Application;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.Executor;

import br.com.jaysonsabino.desafioandroidpopcode.database.AppDatabase;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Specie;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.PeopleListResponseDTO;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.PeopleService;
import br.com.jaysonsabino.desafioandroidpopcode.util.NetworkHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeopleBoundaryCallback extends PagedList.BoundaryCallback<Character> {

    private Application app;
    private final AppDatabase database;
    private final PeopleService peopleService;
    private Executor executor;
    private int actualPage = 0;
    private boolean loading = false;

    PeopleBoundaryCallback(Application app, AppDatabase database, PeopleService peopleService, Executor executor) {
        this.app = app;
        this.database = database;
        this.peopleService = peopleService;
        this.executor = executor;
    }

    @Override
    public void onZeroItemsLoaded() {
        if (!NetworkHelper.isConnected(app)) {
            Toast.makeText(app, "Verifique sua conexão com a internet.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (loading) return;
        loading = true;

        queryNextPage();
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull Character itemAtFront) {
        // nothing to do, already loaded
    }

    @Override
    public void onItemAtEndLoaded(@NonNull Character itemAtEnd) {
        if (!NetworkHelper.isConnected(app)) {
            Toast.makeText(app, "Verifique sua conexão com a internet.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (loading) return;
        loading = true;

        queryNextPage();
    }

    private void queryNextPage() {
        Call<PeopleListResponseDTO> call = peopleService.getList(++actualPage);

        call.enqueue(new Callback<PeopleListResponseDTO>() {
            @Override
            public void onResponse(Call<PeopleListResponseDTO> call, Response<PeopleListResponseDTO> response) {
                PeopleListResponseDTO body = response.body();

                if (body == null) {
                    return;
                }

                List<Character> characters = body.getResults();

                // TODO melhorar esse codigo, verificar se tem algo pronto no Jackson para isso, outra alternativa seria criar um DTO para o serviço e depois convertê-lo na entidade
                for (Character character : characters) {
                    character.setIdByUrl();

                    if (character.getSpecies().size() == 1) {
                        Integer specieId = Specie.getIdByUrl(character.getSpecies().get(0));
                        character.setSpecie(specieId);
                    } else if (character.getSpecies().size() > 1) {
                        Log.e("RETORNO_NAO_ESPERADO", "Nenhum personagem possuía mais de uma espécie no momento da criação do app. Sendo assim simplifiquei a entidade para salvar apenas o id.");
                    }
                }

                insertIntoDatabase(characters);
            }

            @Override
            public void onFailure(Call<PeopleListResponseDTO> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void insertIntoDatabase(final List<Character> characters) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.getCharacterDAO().insert(characters);
                loading = false;
            }
        });
    }
}
