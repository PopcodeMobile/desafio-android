package br.com.jaysonsabino.desafioandroidpopcode.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import java.util.concurrent.Executor;

import br.com.jaysonsabino.desafioandroidpopcode.database.AppDatabase;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.PeopleService;
import br.com.jaysonsabino.desafioandroidpopcode.ui.peoplelist.PeopleBoundaryCallback;
import br.com.jaysonsabino.desafioandroidpopcode.util.NetworkHelper;

public class PeopleRepository {

    private Application app;
    private AppDatabase database;
    private Executor executor;
    private PeopleBoundaryCallback boundaryCallback;
    private PeopleService peopleService;

    public PeopleRepository(Application app, AppDatabase database, PeopleService peopleService, Executor executor) {
        this.app = app;
        this.database = database;
        this.executor = executor;
        this.peopleService = peopleService;

        initBoundaryCallback();
    }

    public void deleteLocalCharactersCacheIfConnected() {
        if (!NetworkHelper.isConnected(app)) return;

        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.getCharacterDAO().deleteAll();
            }
        });
    }

    public LiveData<PagedList<Character>> getPagedList(String queryName, Boolean showOnlyFavorites) {
        DataSource.Factory<Integer, Character> dataSourceFactory;

        if (showOnlyFavorites && queryName != null) {
            dataSourceFactory = database.getCharacterDAO().findAllFavoritesByName("%" + queryName + "%");
        } else if (showOnlyFavorites) {
            dataSourceFactory = database.getCharacterDAO().findAllFavorites();
        } else if (queryName != null) {
            dataSourceFactory = database.getCharacterDAO().findByName("%" + queryName + "%");
        } else {
            dataSourceFactory = database.getCharacterDAO().findAll();
        }

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(8)
                .setPrefetchDistance(24)
                .setInitialLoadSizeHint(24)
                .setEnablePlaceholders(false)
                .build();

        return new LivePagedListBuilder<>(dataSourceFactory, config)
                .setBoundaryCallback(boundaryCallback)
                .build();
    }

    private void initBoundaryCallback() {
        boundaryCallback = new PeopleBoundaryCallback(
                app,
                database,
                peopleService,
                executor
        );
    }
}
