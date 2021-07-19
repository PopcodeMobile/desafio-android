package br.com.star_wars_wiki.database.repository;

import android.app.Application;
import android.app.AsyncNotedAppOp;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

import br.com.star_wars_wiki.database.RoomDatabase;
import br.com.star_wars_wiki.database.dao.PlanetDAO;
import br.com.star_wars_wiki.entity.Planet;

public class PlanetRepo {
    private RoomDatabase database;
    private LiveData<List<Planet>> getAllPlanets;

    public PlanetRepo(Application application){
        database = RoomDatabase.getInstance(application.getBaseContext());
        getAllPlanets = database.planetDAO().getAllPlanets();
    }

    public void insert(Planet planet){
        new InsertAsyncTask(database).execute(planet);
    }

    static class InsertAsyncTask extends AsyncTask<Planet, Void, Void> {
        private PlanetDAO planetDAO;

        InsertAsyncTask(RoomDatabase roomDatabase){
            planetDAO = roomDatabase.planetDAO();
        }
        @Override
        protected Void doInBackground(Planet... planets) {
            planetDAO.insert(planets[0]);
            return null;
        }
    }

    public LiveData<List<Planet>> getAllPlanets(){
        return this.getAllPlanets;
    }
}
