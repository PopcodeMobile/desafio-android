package com.example.administrador.starwarswiki;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterRepository {
    private Webservice webservice;
    // ...
    public LiveData<StarWarsCharacter> getStarWarsCharacter(int id) {
        // This isn't an optimal implementation. We'll fix it later.
        final MutableLiveData<StarWarsCharacter> data = new MutableLiveData<>();

        new RetrofitConfig().getService().getStarWarsCharacter(id).enqueue(new Callback<StarWarsCharacter>() {
            @Override
            public void onResponse(Call<StarWarsCharacter> call, Response<StarWarsCharacter> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<StarWarsCharacter> call, Throwable t) {

            }
        });
        return data;
    }
}