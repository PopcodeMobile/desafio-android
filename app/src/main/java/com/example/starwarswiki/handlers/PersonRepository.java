package com.example.starwarswiki.handlers;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Parcel;

import androidx.lifecycle.LiveData;

import com.example.starwarswiki.structural.People;
import com.example.starwarswiki.structural.Person;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PersonRepository {
    private PeopleDAO peopleDao;
    private LiveData<List<Person>> listOfPerson;

    public PersonRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        peopleDao = db.peopleDAO();
        listOfPerson = peopleDao.getAllPerson();
    }

    public LiveData<List<Person>> getAllPerson() {
        return listOfPerson;
    }

    public void insert (Person person) {
        new insertAsyncTask(peopleDao).execute(person);
    }

    public void insertList(List<Person> listOfPerson) {
        for (int i = 0; i < listOfPerson.size(); i++) {
            insert(listOfPerson.get(i));
        }
    }

    private static class insertAsyncTask extends AsyncTask<Person, Void, Void> {
        private PeopleDAO mAsyncTaskDao;
//        private String jsonURL = "https://swapi.co/api/people/?format=json";
//        private People people;
//        private ObjectMapper objectMapper;

        insertAsyncTask(PeopleDAO dao) { mAsyncTaskDao = dao; }

        @Override
        protected Void doInBackground(final Person... params) {
            //                people = objectMapper.readValue(new URL(jsonURL), People.class);
            mAsyncTaskDao.insertPerson(params[0]);
            return null;
        }
    }



}


