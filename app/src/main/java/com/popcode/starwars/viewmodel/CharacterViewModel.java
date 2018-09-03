package com.popcode.starwars.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.popcode.starwars.data.local.CharacterRepository;
import com.popcode.starwars.data.local.entity.CharacterElement;
import com.popcode.starwars.data.remote.ApiRepository;
import com.popcode.starwars.data.remote.response.GenericResponse;

import javax.inject.Inject;

public class CharacterViewModel extends AndroidViewModel {
    private static final String TAG = CharacterViewModel.class.getName();
    private static final MutableLiveData ABSENT = new MutableLiveData();
    {
        ABSENT.setValue(null);
    }

    private final LiveData<CharacterElement> characterObservable;
    private final LiveData<CharacterElement> characterOfflineObservable;
    private final LiveData<GenericResponse> characterHomeWorldObservable;
    private final LiveData<GenericResponse> characterSpecieObservable;

    private final MutableLiveData<Integer> characterID;
    private final MutableLiveData<String> characterName;
    private final MutableLiveData<Integer> characterHomeWorld;
    private final MutableLiveData<Integer> characterSpecie;

    public ObservableField<CharacterElement> character = new ObservableField<>();
    public ObservableField<String> planet = new ObservableField<>();
    public ObservableField<String> specie = new ObservableField<>();

    private CharacterRepository characterRepository;

    @Inject
    public CharacterViewModel(@NonNull ApiRepository apiRepository, @NonNull Application application) {
        super(application);

        this.characterID = new MutableLiveData<>();
        this.characterName = new MutableLiveData<>();
        this.characterHomeWorld = new MutableLiveData<>();
        this.characterSpecie = new MutableLiveData<>();

        characterRepository = new CharacterRepository(application);

        characterObservable = Transformations.switchMap(characterID, input -> apiRepository.getCharacter(characterID.getValue()));
        characterOfflineObservable = Transformations.switchMap(characterName, input -> characterRepository.getCharacter(input));
        characterHomeWorldObservable = Transformations.switchMap(characterHomeWorld, input -> apiRepository.getPlanet(input));
        characterSpecieObservable = Transformations.switchMap(characterSpecie, input -> apiRepository.getSpecie(input));
    }

    public LiveData<CharacterElement> getObservableCharacter() {
        return characterObservable;
    }

    public LiveData<CharacterElement> getObservableCharacterOffline() {
        return characterOfflineObservable;
    }

    public LiveData<GenericResponse> getCharacterHomeWorldObservable() {
        return characterHomeWorldObservable;
    }

    public LiveData<GenericResponse> getSpecieObservable() {
        return characterSpecieObservable;
    }

    // used for startup request
    public void setSpecieId(Integer specieId) {
        this.characterSpecie.setValue(specieId);
    }

    // used for update ui
    public void setSpecie(String specieName) {
        this.specie.set(specieName);
    }

    // used for startup request
    public void setHomeWorld(Integer planetId){
        this.characterHomeWorld.setValue(planetId);
    }


    // used for update ui
    public void setPlanet(String planetName) {
        this.planet.set(planetName);
    }

    public void setCharacter(CharacterElement characterElement) {
        this.character.set(characterElement);
    }

    public void setCharacterName(String characterElement) {
        this.characterName.setValue(characterElement);
    }

    public void setCharacterID(Integer characterID) {
        this.characterID.setValue(characterID);
    }
}

