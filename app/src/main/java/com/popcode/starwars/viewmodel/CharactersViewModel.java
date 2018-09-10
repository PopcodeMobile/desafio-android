package com.popcode.starwars.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.popcode.starwars.data.local.CharacterRepository;
import com.popcode.starwars.data.local.entity.CharacterElement;
import com.popcode.starwars.data.remote.ApiRepository;
import com.popcode.starwars.data.remote.response.CharactersResponse;

import java.util.List;

import javax.inject.Inject;

public class CharactersViewModel extends AndroidViewModel {

    private final MutableLiveData<Integer> page;
    private final MutableLiveData<List<CharacterElement>> offlineCharacters;

    // define observers
    private LiveData<CharactersResponse> onlineCharactersObservable;
    private LiveData<List<CharacterElement>> offlineCharactersObservable;

    // define repositories
    private ApiRepository apiRepository;
    private CharacterRepository offlineCharactersRepository;

    @Inject
    public CharactersViewModel(@NonNull ApiRepository apiRepository, @NonNull Application application) {
        super(application);

        page = new MutableLiveData<>();
        offlineCharacters = new MutableLiveData<>();

        // set api repository object
        this.apiRepository = apiRepository;
        // set character repository offline object
        this.offlineCharactersRepository = new CharacterRepository(application);


        // define handler for changes
        onlineCharactersObservable = Transformations.switchMap(page, newPage -> this.apiRepository.getCharacters(newPage));
        offlineCharactersObservable = Transformations.switchMap(offlineCharacters, input -> this.offlineCharactersRepository.getAllCharacters(input));
    }

    // setters
    public void setPage(Integer pageCount) {
        page.setValue(pageCount);
    }
    public void setOfflineCharacters(List<CharacterElement> offlineCharacters) {
        this.offlineCharacters.setValue(offlineCharacters);
    }

    // handlers

    /**
     * Provides a observable to handler when online characters change.
     * @return
     */
    public LiveData<CharactersResponse> getOnlineCharactersObservable() {
        return onlineCharactersObservable;
    }
    public LiveData<List<CharacterElement>> getOfflineCharactersRepository() {
        return offlineCharactersObservable;
    }
}
