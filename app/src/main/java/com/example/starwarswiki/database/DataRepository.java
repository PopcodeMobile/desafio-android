package com.example.starwarswiki.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.starwarswiki.structural.FavLogItem;
import com.example.starwarswiki.structural.Person;
import com.example.starwarswiki.structural.Planet;
import com.example.starwarswiki.structural.Specie;

import java.util.List;

public class DataRepository {
    private PeopleDAO peopleDao;
    private PlanetDAO planetDAO;
    private SpecieDAO specieDAO;
    private FavLogDAO favLogDAO;
    private LiveData<List<Person>> listOfPerson;

    public DataRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        peopleDao = db.peopleDAO();
        planetDAO = db.planetDAO();
        specieDAO = db.specieDAO();
        favLogDAO = db.favLogDAO();

        listOfPerson = peopleDao.getAllPerson();
    }

    //Person Operations Begin >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public LiveData<List<Person>> getAllPerson() {
        return listOfPerson;
    }

    public void insert (Person person) {
        new insertAsyncTask(peopleDao).execute(person);
    }

    private static class insertAsyncTask extends AsyncTask<Person, Void, Void> {
        private PeopleDAO mAsyncTaskDao;

        insertAsyncTask(PeopleDAO dao) { mAsyncTaskDao = dao; }

        @Override
        protected Void doInBackground(final Person... params) {
            //                people = objectMapper.readValue(new URL(jsonURL), People.class);
            mAsyncTaskDao.insertPerson(params[0]);
            return null;
        }
    }

    public void insertList(List<Person> listOfPerson) {
        for (int i = 0; i < listOfPerson.size(); i++) {
            insert(listOfPerson.get(i));
        }
    }

    public LiveData<List<Person>> queryByName (String name){
        return peopleDao.searchByName(name);
    }

    public LiveData<Person> queryOnePerson (String name) {
        return peopleDao.getOnePersonByName(name);
    }

    public LiveData<List<Planet>> getAllPlanets() {
        return planetDAO.getAllPlanets();
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

    // >>>>>>>>>> Person Operations End >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    // >>>>>>>>>> Planet Operations Begin >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public void insertPlanet(Planet planet) {
        new PlanetInsertAsyncTask(planetDAO).execute(planet);
    }

    public void insertPlanetList(List<Planet> list) {
        for(int i = 0; i < list.size(); i++) {
            insertPlanet(list.get(i));
        }

    }

    public LiveData<List<Planet>> getHomeworld(String homeworldURL) {
        return planetDAO.getHomeworld(homeworldURL);
    }

    private static class PlanetInsertAsyncTask extends AsyncTask<Planet, Void, Void> {
        private PlanetDAO mAsyncTaskDao;

        PlanetInsertAsyncTask(PlanetDAO planetDAO) { mAsyncTaskDao = planetDAO; }

        @Override
        protected Void doInBackground(Planet... params) {
            mAsyncTaskDao.insertPlanet(params[0]);
            return null;
        }
    }

    // >>>>>>>>>> Planet Operations End >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    // >>>>>>>>>> Species Operarions Begin >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public void insertSpecie(Specie specie) {
        new SpecieInsertAsyncTask(specieDAO).execute(specie);
    }

    public void insertSpecieList(List<Specie> list) {
        for(int i = 0; i < list.size(); i++) {
            insertSpecie(list.get(i));
        }

    }

    public LiveData<List<Specie>> getSpecie(String homeworldURL) {
        return specieDAO.getHomeworld(homeworldURL);
    }

    private static class SpecieInsertAsyncTask extends AsyncTask<Specie, Void, Void> {
        private SpecieDAO mAsyncTaskDao;

        SpecieInsertAsyncTask(SpecieDAO specieDAO) { mAsyncTaskDao = specieDAO; }

        @Override
        protected Void doInBackground(Specie... params) {
            mAsyncTaskDao.insertSpecie(params[0]);
            return null;
        }
    }

    // >>>>>>>>>> Species Operarions End >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    // >>>>>>>>>> FavLog Operarions Begin >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public void logFailedFavItem(FavLogItem result) {
        new InsertFailedFav(favLogDAO).execute(result);
    }

    private static class InsertFailedFav extends AsyncTask<FavLogItem, Void, Void > {
        FavLogDAO myAsyncDAO;
        InsertFailedFav(FavLogDAO favLogDAO) { myAsyncDAO = favLogDAO; }

        @Override
        protected Void doInBackground(FavLogItem... favLogItems) {
            myAsyncDAO.insertLogEntry(favLogItems[0]);
            return null;
        }
    }

    public LiveData<List<FavLogItem>> getAllFailedFavs(){
        LiveData<List<FavLogItem>> list = favLogDAO.getAllFailedFavs();
        return list;
    }

    public void removeAllFailedFav(){
        new RemoveAllFailedFav(favLogDAO).execute();

    }

    private static class RemoveAllFailedFav extends AsyncTask<Void, Void, Void > {
        FavLogDAO myAsyncDAO;
        RemoveAllFailedFav(FavLogDAO favLogDAO) { myAsyncDAO = favLogDAO; }

        @Override
        protected Void doInBackground(Void... voids) {
            myAsyncDAO.removeAllFavLog();
            return null;
        }
    }

    // >>>>>>>>>> FavLog Operarions End >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}


