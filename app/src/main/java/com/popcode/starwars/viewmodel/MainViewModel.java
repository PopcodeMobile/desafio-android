package com.popcode.starwars.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.popcode.starwars.data.remote.ApiRepository;
import com.popcode.starwars.data.remote.response.CharactersResponse;

import javax.inject.Inject;

public class MainViewModel extends AndroidViewModel{

    private final MutableLiveData<String> query;

    // define observers
    private LiveData<CharactersResponse> onlineSearchQueryObservable;

    // repositories
    private ApiRepository apiRepository;

    @Inject
    public MainViewModel(@NonNull ApiRepository apiRepository, @NonNull Application application) {
        super(application);

        query = new MutableLiveData<>();

        // define repositories
        this.apiRepository = apiRepository;

        // define handler for changes
        onlineSearchQueryObservable = Transformations.switchMap(query, search -> this.apiRepository.findCharacter(search));
    }

    public LiveData<CharactersResponse> getOnlineSearchQueryObservable() {
        return onlineSearchQueryObservable;
    }

    // define setters
    public void setQuery(String query){ this.query.setValue(query);}
}
