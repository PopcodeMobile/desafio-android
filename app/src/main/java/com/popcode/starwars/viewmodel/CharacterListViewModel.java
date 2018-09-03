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

public class CharacterListViewModel extends AndroidViewModel {
    private LiveData<List<CharacterElement>> charactersObservable;

    private final MutableLiveData<Integer> page;

    private final MutableLiveData<List<CharacterElement>> elements;

    private final MutableLiveData<String> query;

    private ApiRepository apiRepository;

    private CharacterRepository characterRepository;

    private LiveData<CharactersResponse> characterResponseObservable;
    private LiveData<CharactersResponse> characterSearchObservable;

    @Inject
    public CharacterListViewModel(@NonNull ApiRepository apiRepository, @NonNull Application application) {
        super(application);

        this.apiRepository = apiRepository;

        this.characterRepository = new CharacterRepository(application);

        page = new MutableLiveData<>();
        page.setValue(1);
        query = new MutableLiveData<>();

        elements = new MutableLiveData<>();

        characterResponseObservable = Transformations.switchMap(page, input -> this.apiRepository.getCharacters(page.getValue()));
        characterSearchObservable = Transformations.switchMap(query, input -> this.apiRepository.findCharacter(query.getValue()));
        charactersObservable = Transformations.switchMap(elements, input -> this.characterRepository.getAllCharacters(input));
    }

    public LiveData<List<CharacterElement>> getCharactersOfflineObservable() {
        return charactersObservable;
    }

    public LiveData<CharactersResponse> getCharactersResponseObservable() {
        return characterResponseObservable;
    }

    public LiveData<CharactersResponse> getCharacterSearchObservable() {
        return characterSearchObservable;
    }

    public void setElements(List<CharacterElement> items) {
        elements.setValue(items);
    }

    public void setPage(Integer pageCount) {
        page.setValue(pageCount);
    }

    public void setQuery(String querySearch) {
        query.setValue(querySearch);
    }

}
