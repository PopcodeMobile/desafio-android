package br.com.jaysonsabino.desafioandroidpopcode.ui.peoplelist;

import android.app.Activity;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

import br.com.jaysonsabino.desafioandroidpopcode.database.DatabaseFactory;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;

public class PeopleListViewModel extends ViewModel {

    private final PeopleRepository repository;
    private MutableLiveData<String> queryName = new MutableLiveData<>();
    private LiveData<PagedList<Character>> charactersPagedList;

    PeopleListViewModel(PeopleRepository repository) {
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

    static class Factory implements ViewModelProvider.Factory {

        Executor executor;
        Activity activity;

        public Factory(Executor executor, Activity activity) {
            this.executor = executor;
            this.activity = activity;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new PeopleListViewModel(new PeopleRepository(
                    activity,
                    new DatabaseFactory().getDatabase(activity),
                    executor
            ));
        }
    }
}
