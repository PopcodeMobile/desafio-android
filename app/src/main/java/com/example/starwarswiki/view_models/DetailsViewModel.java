package com.example.starwarswiki.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.starwarswiki.database.DataRepository;
import com.example.starwarswiki.structural.Planet;
import com.example.starwarswiki.structural.Specie;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class DetailsViewModel extends AndroidViewModel {
    private DataRepository dataRepository;
    private LiveData<List<Planet>> listOfPlanet;
    private LiveData<List<Planet>> queryPlanet;
    private LiveData<List<Specie>> querySpecie;

    public DetailsViewModel(@NonNull Application application) {
        super(application);
        dataRepository = new DataRepository(application);
        listOfPlanet = dataRepository.getAllPlanets();
    }

    private String getIdFromPath (String path){
        URI uri = null;
        try {
            uri = new URI(path);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String[] segments = uri.getPath().split("/");
        String strID = segments[segments.length-1];
        return strID;
    }
//  >>>>>>>>>> Planet Operations Begin >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public LiveData<List<Planet>> queryAllPlanets () {
        return listOfPlanet;
    }

    public LiveData<List<Planet>> queryPlanetByPath(String path){
        queryPlanet = dataRepository.getHomeworld(getIdFromPath(path));
        return queryPlanet;
    }
//  >>>>>>>>>> Planet Operations End >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

//  >>>>>>>>>> Specie Operations Begin >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public LiveData<List<Specie>> querySpecieByLIst (String path){
        URI uri = null;
        try {
            uri = new URI(path);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String[] segments = uri.getPath().split("/");
        String strID = segments[segments.length-1];
        querySpecie = dataRepository.getSpecie(strID);
        return querySpecie;
    }

    public LiveData<List<Specie>> querySpecieByList(List<String> speciesURL) {
        for(int i = 0; i < speciesURL.size(); i++ ){
            //Concatanating in case of multiple species
            if(i == 0) {
                querySpecie = dataRepository.getSpecie(getIdFromPath(speciesURL.get(i)));
            } else {
                querySpecie.getValue().addAll(dataRepository.getSpecie(getIdFromPath(speciesURL.get(i))).getValue());
            }
        }
        return querySpecie;
    }
//  >>>>>>>>>> Specie Operations End >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}
