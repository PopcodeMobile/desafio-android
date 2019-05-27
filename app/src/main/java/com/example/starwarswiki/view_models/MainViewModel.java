package com.example.starwarswiki.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.starwarswiki.database.DataRepository;
import com.example.starwarswiki.structural.Person;
import com.example.starwarswiki.structural.Planet;
import com.example.starwarswiki.structural.Specie;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private DataRepository dataRepository;
    private LiveData<List<Person>> lisfOfPerson;
    private LiveData<List<Person>> queryResult;

    public MainViewModel(@NonNull Application application) {
        super(application);
        dataRepository = new DataRepository(application);
        lisfOfPerson = dataRepository.getAllPerson();
    }

    public LiveData<List<Person>> getAllPerson () { return lisfOfPerson; }

    public LiveData<List<Person>> queryByName(String name) {
        queryResult = dataRepository.queryByName(name);

        return queryResult;
    }

    /**
     * Setting as favorite
     * @param name name of the person
     * @param value 0 set false, "anything else" true
     */
    public void setAsFavorite(String name, int value) {
        dataRepository.setFavorite(name, value);
    }

//    public void insert(Person person) { dataRepository.insert(person); }
    public void insert(List<Person> listOfPerson) {
        dataRepository.insertList(listOfPerson);
    }

    public void insertPlanets(List<Planet> listOfPlanet) {
        dataRepository.insertPlanetList(listOfPlanet);
    }

    public void insertSpecies(List<Specie> listOfSpecie) {
        dataRepository.insertSpecieList(listOfSpecie);
    }
}
