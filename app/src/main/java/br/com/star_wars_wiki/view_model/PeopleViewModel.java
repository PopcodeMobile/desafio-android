package br.com.star_wars_wiki.view_model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import br.com.star_wars_wiki.database.dao.PeopleDAO;
import br.com.star_wars_wiki.database.repository.PeopleRepo;
import br.com.star_wars_wiki.entity.People;

public class PeopleViewModel extends AndroidViewModel {
    private PeopleRepo peopleRepo;
    private LiveData<List<People>> getAllPeople;

    public PeopleViewModel(Application application){
        super(application);
        peopleRepo = new PeopleRepo(application);
        getAllPeople = peopleRepo.getAllPeople();
    }

    public void insert(List<People> peopleList){
        peopleRepo.insert(peopleList);
    }

    public LiveData<List<People>> getAllPeople(){
        return getAllPeople;
    }
}
