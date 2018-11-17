package com.example.administrador.starwarswiki;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class DetailsViewModel extends AndroidViewModel {
    private StarWarsRepository starWarsRepository;
    private Application application;
    private RetrofitConfig retrofit;
    private LiveData<StarWarsCharacter> starWarsCharacterLiveData;

    public DetailsViewModel(Application application, int id){
        super(application);
        retrofit = new RetrofitConfig();
        this.starWarsRepository = new StarWarsRepository(application, retrofit.getService(), id);
        this.starWarsCharacterLiveData = starWarsRepository.getCharacter();
    }

    public void updateFavorite(boolean b, int id){
        starWarsRepository.updateFavorite(b,id);
    }

    public void getCharacter(int id){
        starWarsRepository.getCharacterById(id);
    }

    public LiveData<StarWarsCharacter> getCharacter(){
        return this.starWarsCharacterLiveData;
    }

}
