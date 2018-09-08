package br.com.jaysonsabino.desafioandroidpopcode.ui.peoplelist;

import android.app.Activity;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.PagedList;

import java.util.concurrent.Executor;

import br.com.jaysonsabino.desafioandroidpopcode.database.DatabaseFactory;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;

public class PeopleListViewModel {

    private final PeopleRepository repository;
    private MutableLiveData<String> queryName = new MutableLiveData<>();
    private LiveData<PagedList<Character>> charactersPagedList;

    PeopleListViewModel(Activity activity, Executor executor) {
        repository = new PeopleRepository(
                activity,
                new DatabaseFactory().getDatabase(activity),
                executor
        );

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
}
