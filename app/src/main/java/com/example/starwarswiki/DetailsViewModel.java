package com.example.starwarswiki;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.starwarswiki.handlers.DataRepository;
import com.example.starwarswiki.structural.Planet;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class DetailsViewModel extends AndroidViewModel {
    private DataRepository dataRepository;
    private LiveData<List<Planet>> listOfPlanet;
    private LiveData<List<Planet>> queryPlanet;

    public DetailsViewModel(@NonNull Application application) {
        super(application);
        dataRepository = new DataRepository(application);
        listOfPlanet = dataRepository.getAllPlanets();
    }

    public LiveData<List<Planet>> queryAllPlanets () {
        return listOfPlanet;
    }

    public LiveData<List<Planet>> queryByPath (String path){
        URI uri = null;
        try {
            uri = new URI(path);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String[] segments = uri.getPath().split("/");
        String strID = segments[segments.length-1];
        queryPlanet = dataRepository.getHomeworld(strID);
        return queryPlanet;
    }
}
