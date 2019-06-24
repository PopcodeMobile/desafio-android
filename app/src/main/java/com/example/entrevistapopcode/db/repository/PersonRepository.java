package com.example.entrevistapopcode.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.entrevistapopcode.api.entity.entity.Person;
import com.example.entrevistapopcode.db.StarWarsRoomDatabase;
import com.example.entrevistapopcode.db.dao.PersonDAO;

import java.util.List;

public class PersonRepository {

    private PersonDAO mWordDao;
    private LiveData<List<Person>> mAllWords;

    public PersonRepository(Application application) {
        StarWarsRoomDatabase db = StarWarsRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();

    }

    public LiveData<List<Person>> getAllWords() {
        mAllWords = mWordDao.getAllPerson();
        return mAllWords;
    }

    public LiveData<List<Person>> getAllFavorite() {
        mAllWords = mWordDao.getAllFavorite(true);
        return mAllWords;
    }

    public void insertFavorite(String name, boolean value) {
        new insertFavoritetAsyncTask(mWordDao, value).execute(name);

    }

    public LiveData<Person> getPerson(String name) {

        return mWordDao.getPerson(name);
    }


    public void insert(Person word) {
        new insertAsyncTask(mWordDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Person, Void, Void> {

        private PersonDAO mAsyncTaskDao;

        insertAsyncTask(PersonDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Person... params) {
            mAsyncTaskDao.insertPerson(params[0]);
            return null;
        }
    }

    private static class insertFavoritetAsyncTask extends AsyncTask<String, Void, Void> {

        private PersonDAO mAsyncTaskDao;
        private boolean value;

        insertFavoritetAsyncTask(PersonDAO dao, boolean value) {
            mAsyncTaskDao = dao;
            this.value = value;
        }

        @Override
        protected Void doInBackground(final String... params) {
            mAsyncTaskDao.favorite(params[0], value);
            return null;
        }
    }


}


