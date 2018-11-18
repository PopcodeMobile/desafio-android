package com.example.administrador.starwarswiki.ui.CharacterDetails;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.administrador.starwarswiki.network.RetrofitConfig;
import com.example.administrador.starwarswiki.data.StarWarsRepository;
import com.example.administrador.starwarswiki.data.model.StarWarsCharacter;

public class CharacterDetailsViewModel extends AndroidViewModel {
    private StarWarsRepository starWarsRepository;
    private Application application;
    private RetrofitConfig retrofit;
    private LiveData<StarWarsCharacter> starWarsCharacterLiveData;
    private MutableLiveData<String> specie;
    private MutableLiveData<String> planet;
    private MutableLiveData<String> favoriteResponseMessage;


    public CharacterDetailsViewModel(Application application, int id){
        super(application);
        retrofit = new RetrofitConfig();
        this.starWarsRepository = new StarWarsRepository(application, retrofit, id);
        this.starWarsCharacterLiveData = starWarsRepository.getCharacter();
        this.specie = starWarsRepository.getSpecie();
        this.planet = starWarsRepository.getPlanet();
        this.favoriteResponseMessage = starWarsRepository.getFavoriteResponseMessage();

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

    public void getPlanetAndSpecies(int pid, int sid){
        //this calls are asynchronous
        starWarsRepository.getPlanet(pid);
        starWarsRepository.getSpecie(sid);
    }

    public MutableLiveData<String> getSpecie() {
        return specie;
    }

    public MutableLiveData<String> getPlanet() {
        return planet;
    }

    public MutableLiveData<String> getFavoriteResponseMessage() {
        return favoriteResponseMessage;
    }
}
