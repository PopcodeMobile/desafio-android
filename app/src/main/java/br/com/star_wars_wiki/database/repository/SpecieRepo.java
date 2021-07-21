package br.com.star_wars_wiki.database.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.GridLayout;

import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.star_wars_wiki.database.RoomDatabase;
import br.com.star_wars_wiki.database.dao.SpeciesDAO;
import br.com.star_wars_wiki.entity.Planet;
import br.com.star_wars_wiki.entity.Specie;

public class SpecieRepo {
    private RoomDatabase database;
    private LiveData<List<Specie>> getAllSpecies;

    public SpecieRepo(Application application){
        database = RoomDatabase.getInstance(application.getBaseContext());
        getAllSpecies = database.speciesDAO().getAllSpecies();
    }

    public void insert(Specie specie){
        new InsertAsyncTask(database).execute(specie);
    }

    static class InsertAsyncTask extends AsyncTask<Specie, Void, Void> {
        private SpeciesDAO speciesDAO;

        InsertAsyncTask(RoomDatabase roomDatabase){
            speciesDAO = roomDatabase.speciesDAO();
        }

        @Override
        protected Void doInBackground(Specie... specie) {
            speciesDAO.insert(specie[0]);
            return null;
        }
    }

    public LiveData<List<Specie>> getAllSpecies(){
        return this.getAllSpecies;
    }
}
