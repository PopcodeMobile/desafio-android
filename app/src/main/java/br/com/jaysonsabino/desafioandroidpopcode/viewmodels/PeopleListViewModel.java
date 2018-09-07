package br.com.jaysonsabino.desafioandroidpopcode.viewmodels;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import br.com.jaysonsabino.desafioandroidpopcode.database.AppDatabase;
import br.com.jaysonsabino.desafioandroidpopcode.database.DatabaseFactory;
import br.com.jaysonsabino.desafioandroidpopcode.datasources.PageKeyedCharactersDataSourceFactory;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.DatabaseSynchronizer;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.PeopleService;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.ServiceFactory;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.dto.PeopleListResponseDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeopleListViewModel {

    private Activity activity;
    private final AppDatabase database;

    private LiveData<PagedList<Character>> characters;

    public PeopleListViewModel(Activity activity) {
        this.activity = activity;
        database = new DatabaseFactory().getDatabase(activity);

        configurarLivePagedListWithBoundaryCallback();
//        configurarLivePagedListUsingSWAPI();
    }

    public LiveData<PagedList<Character>> getCharacters() {
        return characters;
    }

    private void configurarLivePagedListWithBoundaryCallback() {
        DataSource.Factory<Integer, Character> factory = database.getCharacterDAO().loadCharactersByNameAsc();
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(5)
                .setEnablePlaceholders(true)
                .build();

        final PeopleService peopleService = new ServiceFactory().getPeopleService();
        characters = new LivePagedListBuilder<>(factory, config)
                .setBoundaryCallback(new PagedList.BoundaryCallback<Character>() {

                    private int actualPage = 0;
                    private boolean loading = false;

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

                                // hack to set id of all returned characters
                                for (Character character : characters) {
                                    character.setIdByUrl();
                                }

                                database.getCharacterDAO().insert(body.getResults());
                                loading = false;
                            }

                            @Override
                            public void onFailure(Call<PeopleListResponseDTO> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void onZeroItemsLoaded() {
                        if (loading) return;
                        loading = true;

                        Toast.makeText(activity, "onZeroItemsLoaded " + actualPage, Toast.LENGTH_SHORT).show();

                        queryNextPage();
                    }

                    @Override
                    public void onItemAtFrontLoaded(@NonNull Character itemAtFront) {
                        // nothing to do, already loaded
                    }

                    @Override
                    public void onItemAtEndLoaded(@NonNull Character itemAtEnd) {
                        if (loading) return;
                        loading = true;

                        Toast.makeText(activity, "onItemAtEndLoaded " + actualPage, Toast.LENGTH_SHORT).show();

                        queryNextPage();
                    }
                })
                .build();
    }

    private void configurarLivePagedListUsingSWAPI() {
        PageKeyedCharactersDataSourceFactory factory = new PageKeyedCharactersDataSourceFactory();
        PagedList.Config config = new PagedList.Config.Builder().setPageSize(10).setEnablePlaceholders(false).build();

        characters = new LivePagedListBuilder<>(factory, config).build();
    }

    /**
     * TODO temporario
     */
    public void apagarPersonagensBancoLocal() {
        database.getCharacterDAO().deleteAll();
    }

    public void sincronizarBancoDePersonagens() {
        if (new Random().nextInt(2) == 1) {
            Toast.makeText(activity, "Simulando banco local desatualizado", Toast.LENGTH_LONG).show();
            database.getCharacterDAO().deleteSomeForTestPurposes();
        }

        new DatabaseSynchronizer(activity, database).syncCharacters();
    }
}
