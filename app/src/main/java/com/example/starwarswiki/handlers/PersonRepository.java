package com.example.starwarswiki.handlers;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.example.starwarswiki.structural.Person;
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

    private static class insertAsyncTask extends AsyncTask<Person, Void, Void> {

        private PeopleDAO mAsyncTaskDao;

        insertAsyncTask(PeopleDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Person... params) {
            mAsyncTaskDao.insertPerson(params[0]);
            return null;
        }
    }

}


