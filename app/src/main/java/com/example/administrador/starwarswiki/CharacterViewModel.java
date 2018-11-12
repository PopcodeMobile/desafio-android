package com.example.administrador.starwarswiki;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private LiveData<StarWarsCharacter> starWarsCharacter;
    private CharacterRepository characterRepository;
    private Webservice webservice;

    public CharacterViewModel(CharacterRepository characterRepository){
        this.characterRepository = characterRepository;
    }

    public void init(int id) {
        if (this.starWarsCharacter != null) {
            // ViewModel is created on a per-Fragment basis, so the userId
            // doesn't change.
            return;
        }
        starWarsCharacter = characterRepository.getStarWarsCharacter(id);

    }

    public LiveData<StarWarsCharacter> getStarWarsCharacter() {
        return starWarsCharacter;
    }
}
