package br.com.jaysonsabino.desafioandroidpopcode.ui.peoplelist;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

import br.com.jaysonsabino.desafioandroidpopcode.database.AppDatabase;
import br.com.jaysonsabino.desafioandroidpopcode.database.DatabaseFactory;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;
import br.com.jaysonsabino.desafioandroidpopcode.repository.PeopleRepository;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.PeopleService;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.ServiceFactory;

public class PeopleListViewModel extends ViewModel {

    private final PeopleRepository repository;
    private MutableLiveData<String> queryName = new MutableLiveData<>();
    private LiveData<PagedList<Character>> charactersPagedList;

    private PeopleListViewModel(PeopleRepository repository) {
        this.repository = repository;

        repository.deleteLocalCharactersCacheIfConnected();

        initPagedList();

        search(null);
    }

    public LiveData<PagedList<Character>> getCharactersPagedList() {
        return charactersPagedList;
    }

    public void search(String name) {
        queryName.setValue(name);
    }

    private void initPagedList() {
        charactersPagedList = Transformations.switchMap(queryName, new Function<String, LiveData<PagedList<Character>>>() {
            @Override
            public LiveData<PagedList<Character>> apply(String input) {
                return repository.getPagedList(input);
            }
        });
    }

    public static class Factory implements ViewModelProvider.Factory {

        private Application app;
        private Executor executor;
        private AppDatabase database;
        private PeopleService peopleService;

        public Factory(Application app, Executor executor) {
            this.app = app;
            this.executor = executor;
            database = new DatabaseFactory().getDatabase(app);
            peopleService = new ServiceFactory().getPeopleService();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            PeopleRepository repository = new PeopleRepository(app, database, peopleService, executor);

            return (T) new PeopleListViewModel(repository);
        }
    }
}
