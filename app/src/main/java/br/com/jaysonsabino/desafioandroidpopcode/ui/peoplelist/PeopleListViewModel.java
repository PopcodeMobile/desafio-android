package br.com.jaysonsabino.desafioandroidpopcode.ui.peoplelist;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import br.com.jaysonsabino.desafioandroidpopcode.database.AppDatabase;
import br.com.jaysonsabino.desafioandroidpopcode.database.DatabaseFactory;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.ServiceFactory;

public class PeopleListViewModel {

    private Activity activity;
    private final AppDatabase database;

    private LiveData<PagedList<Character>> characters;

    PeopleListViewModel(Activity activity) {
        this.activity = activity;
        database = new DatabaseFactory().getDatabase(activity);

        configurarLivePagedListWithBoundaryCallback();
    }

    public LiveData<PagedList<Character>> getCharacters() {
        return characters;
    }

    private void configurarLivePagedListWithBoundaryCallback() {
        DataSource.Factory<Integer, Character> factory = database.getCharacterDAO().findAll();
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(10)
                .setInitialLoadSizeHint(10)
                .setEnablePlaceholders(false)
                .build();

        characters = new LivePagedListBuilder<>(factory, config)
                .setBoundaryCallback(new PeopleBoundaryCallback(
                        activity,
                        database,
                        new ServiceFactory().getPeopleService())
                )
                .build();
    }

    /**
     * TODO temporario
     */
    public void apagarPersonagensBancoLocal() {
        database.getCharacterDAO().deleteAll();
    }
}
