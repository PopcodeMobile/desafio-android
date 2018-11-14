package com.example.administrador.starwarswiki;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterViewModel extends ViewModel {
    private List<StarWarsCharacter> starWarsCharacters;
    private CharacterRepository characterRepository;
    private Webservice webservice;

    public CharacterViewModel(CharacterRepository characterRepository){
        this.characterRepository = characterRepository;
        this.webservice = new RetrofitConfig().getService();
        this.starWarsCharacters = new ArrayList<StarWarsCharacter>();

    }

    public void start(){

        Call<PeopleList> call = webservice.getStarWarsCharacters();
        try {
            PeopleList response = call.execute().body();
            for (StarWarsCharacter starWarsCharacter : response.getResults()) {
                characterRepository.insertCharacter(starWarsCharacter);
                this.starWarsCharacters.add(starWarsCharacter);
                Log.d("aqui","auiiiii");
                //textViewName.setText(starWarsCharacter.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/*
    public void init(int id) {
        if (this.starWarsCharacter != null) {
            // ViewModel is created on a per-Fragment basis, so the userId
            // doesn't change.
            return;
        }
        this.starWarsCharacter = characterRepository.getStarWarsCharacter(id);

    }

    public LiveData<StarWarsCharacter> getStarWarsCharacter() {
        return starWarsCharacter;
    }
    */
}
