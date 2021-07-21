package br.com.star_wars_wiki.database.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.star_wars_wiki.database.RoomDatabase;
import br.com.star_wars_wiki.database.dao.PeopleDAO;
import br.com.star_wars_wiki.entity.People;

public class PeopleRepo {
    private RoomDatabase database;
    private LiveData<List<People>> getAllPeople;

    public PeopleRepo(Application application){
        database = RoomDatabase.getInstance(application.getBaseContext());
        getAllPeople = database.peopleDao().getAllPeople();
    }

    public LiveData<List<People>> getAllPeople(){
        return this.getAllPeople;
    }

    public void insert(List<People> peopleList){
        new InsertAsyncTask(database).execute(peopleList);
    }

    static class InsertAsyncTask extends AsyncTask<List<People>, Void, Void>{
        private PeopleDAO peopleDAO;

        InsertAsyncTask(RoomDatabase roomDatabase){
            peopleDAO = roomDatabase.peopleDao();
        }

        @Override
        protected Void doInBackground(List<People>... lists) {
            peopleDAO.insertAllPeople(lists[0]);
            return null;
        }
    }
}
