package br.com.star_wars_wiki.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.star_wars_wiki.database.repository.PlanetRepo;
import br.com.star_wars_wiki.entity.Planet;

public class PlanetViewModel extends AndroidViewModel {
    private PlanetRepo planetRepo;
    private LiveData<List<Planet>> getAllPlanets;

    public PlanetViewModel(Application application) {
        super(application);
        planetRepo = new PlanetRepo(application);
        getAllPlanets = planetRepo.getAllPlanets();
    }

    public void insert(Planet planet){
        planetRepo.insert(planet);
    }

    public LiveData<List<Planet>> getAllPlanets(){
        return this.getAllPlanets;
    }

    public Planet getPlanet(String planetName){
        for(Planet planet : getAllPlanets.getValue()){
            if(planet.getName().equals(planetName)){
                return planet;
            }
        }
        return null;
    }
}
