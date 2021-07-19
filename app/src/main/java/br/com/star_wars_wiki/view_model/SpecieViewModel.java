package br.com.star_wars_wiki.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.star_wars_wiki.database.repository.SpecieRepo;
import br.com.star_wars_wiki.entity.Planet;
import br.com.star_wars_wiki.entity.Specie;

public class SpecieViewModel extends AndroidViewModel {
    private SpecieRepo specieRepo;
    private LiveData<List<Specie>> getAllSpecies;

    public SpecieViewModel(Application application) {
        super(application);
        specieRepo = new SpecieRepo(application);
        getAllSpecies = specieRepo.getAllSpecies();
    }

    public void insert(Specie specie){
        specieRepo.insert(specie);
    }

    public LiveData<List<Specie>> getAllSpecies(){
        return this.getAllSpecies();
    }

    public Specie getSpecie(String specieName){
        for(Specie specie : getAllSpecies.getValue()){
            if(specie.getName().equals(specieName)){
                return specie;
            }
        }
        return null;
    }
}
