package com.example.starwarswiki.handlers;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.starwarswiki.structural.Person;
import com.example.starwarswiki.structural.Planet;

import java.util.List;

public class DataRepository {
    private PeopleDAO peopleDao;
    private PlanetDAO planetDAO;
    private LiveData<List<Person>> listOfPerson;

    public DataRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        peopleDao = db.peopleDAO();
        planetDAO = db.planetDAO();
        listOfPerson = peopleDao.getAllPerson();
    }

    //Person Operations Begin

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

    public LiveData<List<Person>> queryByName (String name){
        return peopleDao.searchByName(name);
    }

    public LiveData<List<Planet>> getAllPlanets() {
        return planetDAO.getAllPlanets();
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


    public void setFavorite (String name, int fav) {
        Person person = new Person();
        person.setName(name);
        person.setFavorite(fav);
        new updateAsyntTask(peopleDao).execute(person);
    }

    private class updateAsyntTask extends AsyncTask <Person, Void, Void> {
        private PeopleDAO mAsyncTaskDao;

        public updateAsyntTask(PeopleDAO peopleDao) { mAsyncTaskDao = peopleDao; }

        @Override
        protected Void doInBackground(Person... people) {
            mAsyncTaskDao.updateFavorite(people[0].getName(),people[0].getFavorite());
            return null;
        }
    }

    //Person Operations End

    //Planet Operations Begin

    public void insertPlanet(Planet planet) {
        new planetInsertAsyncTask(planetDAO).execute(planet);
    }

    public void insertPlanetList(List<Planet> list) {
        for(int i = 0; i < list.size(); i++) {
            insertPlanet(list.get(i));
        }

    }

    public LiveData<List<Planet>> getHomeworld(String homeworldURL) {
        return planetDAO.getHomeworld(homeworldURL);
    }

    private static class planetInsertAsyncTask extends AsyncTask<Planet, Void, Void> {
        private PlanetDAO mAsyncTaskDao;

        planetInsertAsyncTask(PlanetDAO planetDAO) { mAsyncTaskDao = planetDAO; }

        @Override
        protected Void doInBackground(Planet... params) {
            mAsyncTaskDao.insertPlanet(params[0]);
            return null;
        }
    }

    //Planet Operations End

}


