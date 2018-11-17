package com.example.administrador.starwarswiki;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import java.util.List;

public class CharacterViewModel extends AndroidViewModel {
    private LiveData<List<StarWarsCharacter>> starWarsCharactersList;
    private StarWarsRepository starWarsRepository;
    private Application application;
    private RetrofitConfig retrofit;

    public CharacterViewModel(Application application){
        super(application);
        retrofit = new RetrofitConfig();
        this.starWarsRepository = new StarWarsRepository(application, retrofit.getService());
        this.starWarsCharactersList = starWarsRepository.getCharacters();

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

}
