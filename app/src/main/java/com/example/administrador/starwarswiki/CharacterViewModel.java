package com.example.administrador.starwarswiki;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import java.util.List;

public class CharacterViewModel extends AndroidViewModel {
    private LiveData<List<StarWarsCharacter>> starWarsCharactersList;
    private CharacterRepository characterRepository;
    private Application application;
    private RetrofitConfig retrofit;

    public CharacterViewModel(Application application){
        super(application);
        retrofit = new RetrofitConfig();
        this.characterRepository = new CharacterRepository(application, retrofit.getService());
        this.starWarsCharactersList = characterRepository.getCharacters();

    }

    public LiveData<List<StarWarsCharacter>> getStarWarsCharactersList() {
        return starWarsCharactersList;
    }

    public String getNextList() {
        return characterRepository.getNext_list();
    }

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        characterRepository.loadNextDataFromApi(offset);
    }

    public void loadDatabase(){
        characterRepository.fetchInitialData();

    }

}
