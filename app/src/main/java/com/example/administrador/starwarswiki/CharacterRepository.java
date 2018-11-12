package com.example.administrador.starwarswiki;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
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

        Call<StarWarsCharacter> call = new RetrofitConfig().getService().getStarWarsCharacter(id);
        call.enqueue(new Callback<StarWarsCharacter>() {
            @Override
            public void onResponse(Call<StarWarsCharacter> call, Response<StarWarsCharacter> response) {
                data.setValue(response.body());
                Log.d("call", String.valueOf(call.request().url()));
                //Log.d("valor +>", response.body().toString());
            }

            @Override
            public void onFailure(Call<StarWarsCharacter> call, Throwable t) {
                Log.d("ERROR", "deu erro",t);
            }
        });
        return data;
    }
}