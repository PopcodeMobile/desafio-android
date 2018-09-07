package br.com.jaysonsabino.desafioandroidpopcode.ui.peoplelist;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import java.util.concurrent.Executor;

import br.com.jaysonsabino.desafioandroidpopcode.database.AppDatabase;
import br.com.jaysonsabino.desafioandroidpopcode.database.DatabaseFactory;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.ServiceFactory;

public class PeopleListViewModel {

    private Activity activity;
    private final AppDatabase database;
    private Executor executor;

    private LiveData<PagedList<Character>> characters;

    PeopleListViewModel(Activity activity, Executor executor) {
        this.activity = activity;
        database = new DatabaseFactory().getDatabase(activity);
        this.executor = executor;

        configurarLivePagedListWithBoundaryCallback();
    }

    public LiveData<PagedList<Character>> getCharacters() {
        return characters;
    }

    private void configurarLivePagedListWithBoundaryCallback() {
        DataSource.Factory<Integer, Character> factory = database.getCharacterDAO().findAll();
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(8)
                .setPrefetchDistance(24)
                .setInitialLoadSizeHint(24)
                .setEnablePlaceholders(false)
                .build();

        characters = new LivePagedListBuilder<>(factory, config)
                .setBoundaryCallback(new PeopleBoundaryCallback(
                        activity,
                        database,
                        new ServiceFactory().getPeopleService(),
                        executor
                ))
                .build();
    }

    /**
     * TODO temporario
     */
    public void apagarPersonagensBancoLocal() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.getCharacterDAO().deleteAll();
            }
        });
    }
}
