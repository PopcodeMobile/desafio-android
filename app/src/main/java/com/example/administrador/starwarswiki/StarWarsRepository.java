package com.example.administrador.starwarswiki;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StarWarsRepository {
    private String DATABASE_NAME = "starwars_db";
    private StarWarsDatabase starWarsDatabase;
    private Webservice webservice;
    private LiveData<List<StarWarsCharacter>> starWarsCharacterlist;

    public StarWarsRepository(Application application, Webservice webservice) {
        starWarsDatabase = StarWarsDatabase.getDatabase(application);
        this.webservice = webservice;
        this.starWarsCharacterlist = starWarsDatabase.starWarsCharacterDao().getAllCharacters();
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

    public void fetchInitialData(){
        Call<PeopleList> call = webservice.getStarWarsCharacters();
        call.enqueue(new Callback<PeopleList>() {
            @Override
            public void onResponse(Call<PeopleList> call, Response<PeopleList> response) {
                if(response.isSuccessful()) {
                    for (StarWarsCharacter starWarsCharacter : response.body().getResults()) {
                        starWarsCharacter.setId(Integer.parseInt(starWarsCharacter.getUrl().replaceAll("[^\\d]", "")));
                        starWarsCharacter.setFavorite(false);
                        upsertCharacter(starWarsCharacter);
                        Log.d("debug", "fetching initial data");
                    }
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
        Call<PeopleList> call = webservice.getStarWarsCharacters(offset);
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