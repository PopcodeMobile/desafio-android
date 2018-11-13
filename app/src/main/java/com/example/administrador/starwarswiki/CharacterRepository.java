package com.example.administrador.starwarswiki;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//@Singleton

public class CharacterRepository {
    /*    private final Webservice webservice;
        private final StarWarsCharacterDao starWarsCharacterDao;
        private final Executor executor;

        @Inject
        public CharacterRepository(Webservice webservice, StarWarsCharacterDao starWarsCharacterDao, Executor executor) {
            this.webservice = webservice;
            this.starWarsCharacterDao = starWarsCharacterDao;
            this.executor = executor;
        }

        /*public LiveData<StarWarsCharacter> getStarWarsCharacter(int id) {
            refreshCharacter(id);
            // Returns a LiveData object directly from the database.
            return starWarsCharacterDao.load(id);
        }

        private void refreshCharacter(final int id) {
            // Runs in a background thread.
            executor.execute(() -> {
                // Check if user data was fetched recently.
                boolean characterExists = false;
                if (starWarsCharacterDao.checkExist(id) == 1)
                    characterExists = true;
                if (!characterExists) {
                    // Refreshes the data.
                    Response<StarWarsCharacter> response = null;
                    try {
                        response = webservice.getStarWarsCharacter(id).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Check for errors here.

                    // Updates the database. The LiveData object automatically
                    // refreshes, so we don't need to do anything else here.
                    starWarsCharacterDao.save(response.body());
                }
            });
        }

    /*
        public LiveData<StarWarsCharacter> getStarWarsCharacter(int id) {
            refreshCharacter(id);
            // Returns a LiveData object directly from the database.
            return starWarsCharacterDao.load(id);
        }

        private void refreshCharacter(final int id) {
            // Runs in a background thread.
            executor.execute(() -> {
                // Check if user data was fetched recently.
                boolean characterExists = false;
                if (starWarsCharacterDao.checkExist(id) == 1)
                    characterExists = true;
                if (!characterExists) {
                    // Refreshes the data.
                    Response<StarWarsCharacter> response = null;
                    try {
                        response = webservice.getStarWarsCharacter(id).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Check for errors here.

                    // Updates the database. The LiveData object automatically
                    // refreshes, so we don't need to do anything else here.
                    starWarsCharacterDao.save(response.body());
                }
            });
        }
     */
    private String DATABASE_NAME = "starwars_db";
    private StarWarsDatabase starWarsDatabase;

    public CharacterRepository(Context context) {
        starWarsDatabase = Room.databaseBuilder(context,
                StarWarsDatabase.class, DATABASE_NAME).build();
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
}