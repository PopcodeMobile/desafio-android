package com.example.administrador.starwarswiki;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.os.RecoverySystem;
import android.util.Log;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class starWarsRepository {
    private String DATABASE_NAME = "starwars_db";
    private StarWarsDatabase starWarsDatabase;
    private Webservice webservice;
    private LiveData<List<StarWarsCharacter>> starWarsCharacterlist;
    private LiveData<List<Favorite>> favoriteList;

    public starWarsRepository(Application application, Webservice webservice) {
        starWarsDatabase = StarWarsDatabase.getDatabase(application);
        this.webservice = webservice;
        this.starWarsCharacterlist = starWarsDatabase.starWarsCharacterDao().getAllCharacters();
        this.favoriteList = starWarsDatabase.favoriteDao().getAllFavorites();
    }

    public void insertCharacter(StarWarsCharacter starWarsCharacter) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                starWarsDatabase.starWarsCharacterDao().save(starWarsCharacter);
                return null;
            }
        }.execute();
    }

    public void insertFavorite(Integer id) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                starWarsDatabase.favoriteDao().save(new Favorite(id));
                return null;
            }
        }.execute();
    }

    public LiveData<List<StarWarsCharacter>> getCharacters(){
        return starWarsCharacterlist;
    }

    public LiveData<List<Favorite>> getFavoriteList() {
        return favoriteList;
    }

    public void deleteFavorite(int i){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                starWarsDatabase.favoriteDao().deleteFavorite(i);
                return null;
            }
        }.execute();
    }

    public void fetchInitialData(){
        Call<PeopleList> call = webservice.getStarWarsCharacters();
        call.enqueue(new Callback<PeopleList>() {
            @Override
            public void onResponse(Call<PeopleList> call, Response<PeopleList> response) {
                if(response.isSuccessful()) {
                    for (StarWarsCharacter starWarsCharacter : response.body().getResults()) {
                        starWarsCharacter.setId(Integer.parseInt(starWarsCharacter.getUrl().replaceAll("[^\\d]", "")));
                        insertCharacter(starWarsCharacter);
                        Log.d("debug", "fetching initial data");
                    }
                }
            }

            @Override
            public void onFailure(Call<PeopleList> call, Throwable t) {
                Log.d("ERROR", "deu erro", t);
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
                        insertCharacter(starWarsCharacter);
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
}