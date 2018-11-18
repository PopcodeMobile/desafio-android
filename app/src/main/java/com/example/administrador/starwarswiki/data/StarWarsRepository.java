package com.example.administrador.starwarswiki.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.administrador.starwarswiki.data.dao.StarWarsCharacterDao;
import com.example.administrador.starwarswiki.network.SwapiService;
import com.example.administrador.starwarswiki.data.model.PeopleList;
import com.example.administrador.starwarswiki.data.model.Planet;
import com.example.administrador.starwarswiki.data.model.Specie;
import com.example.administrador.starwarswiki.data.model.StarWarsCharacter;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StarWarsRepository {
    private String DATABASE_NAME = "starwars_db";
    private StarWarsDatabase starWarsDatabase;
    private SwapiService swapiService;
    private LiveData<List<StarWarsCharacter>> starWarsCharacterlist;
    private LiveData<StarWarsCharacter> starWarsCharacterLiveData;
    private MutableLiveData<String> specie;
    private MutableLiveData<String> planet;

    public StarWarsRepository(Application application, SwapiService swapiService) {
        starWarsDatabase = StarWarsDatabase.getDatabase(application);
        this.swapiService = swapiService;
        this.starWarsCharacterlist = starWarsDatabase.starWarsCharacterDao().getAllCharacters();
    }

    public StarWarsRepository(Application application, SwapiService swapiService, int id) {
        starWarsDatabase = StarWarsDatabase.getDatabase(application);
        this.swapiService = swapiService;
        this.starWarsCharacterLiveData = starWarsDatabase.starWarsCharacterDao().getCharcter(id);
        this.specie = new MutableLiveData<String>();
        this.planet = new MutableLiveData<String>();
    }

    public void getSpecie(int id){
        Call<Specie> call = swapiService.getSpecies(id);
        //PARALLEL
        //THIS CALL IS AN ASYNC CALL
        call.enqueue(new Callback<Specie>() {
            @Override
            public void onResponse(Call<Specie> call, Response<Specie> response) {
                if(response.isSuccessful()) {
                   Log.d("debug", "fetching specie");
                   specie.setValue(response.body().getName());
                }
            }

            @Override
            public void onFailure(Call<Specie> call, Throwable t) {
                Log.d("ERROR", t.getMessage(), t);
            }
        });
    }

    public void getPlanet(int id){
        Call<Planet> call = swapiService.getPlanet(id);
        //PARALLEL
        //THIS CALL IS AN ASYNC CALL
        call.enqueue(new Callback<Planet>() {
            @Override
            public void onResponse(Call<Planet> call, Response<Planet> response) {
                if(response.isSuccessful()) {
                    Log.d("debug", "fetching specie");
                    planet.setValue(response.body().getName());
                }
            }

            @Override
            public void onFailure(Call<Planet> call, Throwable t) {
                Log.d("ERROR", t.getMessage(), t);
            }
        });
    }

    public void getCharacterById(int id){
        new getCharacterAsyncTask(starWarsDatabase.starWarsCharacterDao()).execute(id);
    }

    public void upsertCharacter(StarWarsCharacter starWarsCharacter) {
        new upsertAsyncTask(starWarsDatabase.starWarsCharacterDao()).execute(starWarsCharacter);
    }

    public void insertCharacter(StarWarsCharacter starWarsCharacter) {
        new insertAsyncTask(starWarsDatabase.starWarsCharacterDao()).execute(starWarsCharacter);
    }

    public void updateFavorite(boolean favorite, int i){
        MyParams params = new MyParams(favorite,i);
        new updateFavoriteAsyncTask(starWarsDatabase.starWarsCharacterDao()).execute(params);
    }

    public LiveData<List<StarWarsCharacter>> getCharacters(){
        return starWarsCharacterlist;
    }

    public LiveData<StarWarsCharacter> getCharacter(){
        return starWarsCharacterLiveData;
    }

    public MutableLiveData<String> getSpecie() {
        return specie;
    }

    public MutableLiveData<String> getPlanet() {
        return planet;
    }



    public void fetchInitialData(){
        Call<PeopleList> call = swapiService.getStarWarsCharacters();
        call.enqueue(new Callback<PeopleList>() {
            @Override
            public void onResponse(Call<PeopleList> call, Response<PeopleList> response) {
                Log.d("debug", "fetching initial data");
                if(response.isSuccessful()) {
                    for (StarWarsCharacter starWarsCharacter : response.body().getResults()) {
                        starWarsCharacter.setId(Integer.parseInt(starWarsCharacter.getUrl().replaceAll("[^\\d]", "")));
                        starWarsCharacter.setFavorite(false);
                        upsertCharacter(starWarsCharacter);
                    }
                    Log.d("debug", "success");
                }
            }

            @Override
            public void onFailure(Call<PeopleList> call, Throwable t) {
                Log.d("ERROR", t.getMessage(), t);
            }
        });
    }

    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        Call<PeopleList> call = swapiService.getStarWarsCharacters(offset);
        call.enqueue(new Callback<PeopleList>() {
            @Override
            public void onResponse(Call<PeopleList> call, Response<PeopleList> response) {
                if (response.isSuccessful()) {
                    for (StarWarsCharacter starWarsCharacter : response.body().getResults()) {
                        starWarsCharacter.setId(Integer.parseInt(starWarsCharacter.getUrl().replaceAll("[^\\d]", "")));
                        upsertCharacter(starWarsCharacter);
                    }
                    Log.d("debug", "loading next page");
                }
            }

            @Override
            public void onFailure(Call<PeopleList> call, Throwable t) {
                Log.d("ERROR", "deu erro",t);
            }
        });
    }

    private static class insertAsyncTask extends AsyncTask<StarWarsCharacter, Void, Void> {
        private StarWarsCharacterDao mAsyncTaskDao;
        insertAsyncTask(StarWarsCharacterDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final StarWarsCharacter... params) {
            mAsyncTaskDao.save(params[0]);
            return null;
        }
    }

    private static class upsertAsyncTask extends AsyncTask<StarWarsCharacter, Void, Void> {
        private StarWarsCharacterDao mAsyncTaskDao;
        upsertAsyncTask(StarWarsCharacterDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final StarWarsCharacter... params) {
            Log.d("><<<>>>>>>>>",params[0].getName());
            long id = mAsyncTaskDao.save(params[0]);
            if (id == -1) {
                Log.d("><<<>>>>>>>>",params[0].getGender());
                params[0].setFavorite(mAsyncTaskDao.load(params[0].getId()).isFavorite());
                mAsyncTaskDao.update(params[0]);
            }
            return null;
        }
    }

    private static class updateFavoriteAsyncTask extends AsyncTask<MyParams, Void , Void> {
        private StarWarsCharacterDao mAsyncTaskDao;
        updateFavoriteAsyncTask(StarWarsCharacterDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(MyParams... params) {
           mAsyncTaskDao.updateFavorite( params[0].isB(), params[0].getI());
           return null;
        }
    }

    private static class getCharacterAsyncTask extends AsyncTask<Integer, Void , Void> {
        private StarWarsCharacterDao mAsyncTaskDao;
        getCharacterAsyncTask(StarWarsCharacterDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(Integer... params) {
            mAsyncTaskDao.load(params[0]);
            return null;
        }
    }

    private class MyParams{
        private boolean b;
        private int i;

        public MyParams(boolean b, int i) {
            this.b = b;
            this.i = i;
        }

        public boolean isB() {
            return b;
        }

        public void setB(boolean b) {
            this.b = b;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }
    }

}