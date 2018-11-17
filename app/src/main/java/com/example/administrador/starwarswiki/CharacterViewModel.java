package com.example.administrador.starwarswiki;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import java.util.List;

public class CharacterViewModel extends AndroidViewModel {
    private LiveData<List<StarWarsCharacter>> starWarsCharactersList;
    private StarWarsRepository StarWarsRepository;
    private Application application;
    private RetrofitConfig retrofit;

    public CharacterViewModel(Application application){
        super(application);
        retrofit = new RetrofitConfig();
        this.StarWarsRepository = new StarWarsRepository(application, retrofit.getService());
        this.starWarsCharactersList = StarWarsRepository.getCharacters();

    }

    public LiveData<List<StarWarsCharacter>> getStarWarsCharactersList() {
        return starWarsCharactersList;
    }

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        StarWarsRepository.loadNextDataFromApi(offset);
    }

    public void loadDatabase(){
        StarWarsRepository.fetchInitialData();

    }

    public void updateFavorite(boolean b, int id){
        StarWarsRepository.updateFavorite(b,id);
    }

}
