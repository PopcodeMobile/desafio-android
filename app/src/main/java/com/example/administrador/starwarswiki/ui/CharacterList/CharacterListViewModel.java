package com.example.administrador.starwarswiki.ui.CharacterList;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.administrador.starwarswiki.network.RetrofitConfig;
import com.example.administrador.starwarswiki.data.StarWarsRepository;
import com.example.administrador.starwarswiki.data.model.StarWarsCharacter;

import java.util.List;

public class CharacterListViewModel extends AndroidViewModel {
    private LiveData<List<StarWarsCharacter>> starWarsCharactersList;
    private StarWarsRepository starWarsRepository;
    private RetrofitConfig retrofit;
    private MutableLiveData<String> favoriteResponseMessage;

    public CharacterListViewModel(Application application){
        super(application);
        retrofit = new RetrofitConfig();
        this.starWarsRepository = new StarWarsRepository(application, retrofit);
        this.starWarsCharactersList = starWarsRepository.getCharacters();
        this.favoriteResponseMessage = starWarsRepository.getFavoriteResponseMessage();
    }

    public MutableLiveData<String> getFavoriteResponseMessage() {
        return favoriteResponseMessage;
    }

    public LiveData<List<StarWarsCharacter>> getStarWarsCharactersList() {
        return starWarsCharactersList;
    }

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        starWarsRepository.loadNextDataFromApi(offset);
    }

    public void loadDatabase(){
        starWarsRepository.fetchInitialData();

    }

    public void updateFavorite(boolean b, int id){
        starWarsRepository.updateFavorite(b,id);
    }

    public void dispatchPendingFavorites(){
        starWarsRepository.dispatchPendingFavorites();
    }

}
