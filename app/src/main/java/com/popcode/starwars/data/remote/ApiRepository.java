package com.popcode.starwars.data.remote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.popcode.starwars.data.local.entity.CharacterElement;
import com.popcode.starwars.data.remote.response.CharactersResponse;
import com.popcode.starwars.data.remote.response.GenericResponse;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRepository {

    ApiService apiService;

    @Inject
    public ApiRepository(ApiService apiService){
        this.apiService = apiService;
    }

    public LiveData<CharactersResponse> getCharacters(Integer page) {
        final MutableLiveData<CharactersResponse> data = new MutableLiveData<>();

        this.apiService.getCharacters(page).enqueue(new Callback<CharactersResponse>() {
            @Override
            public void onResponse(Call<CharactersResponse> call, Response<CharactersResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CharactersResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<GenericResponse> getPlanet(Integer planetId) {
        final MutableLiveData<GenericResponse> data = new MutableLiveData<>();

        this.apiService.getPlanet(planetId).enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<GenericResponse> getSpecie(Integer specieId) {
        final MutableLiveData<GenericResponse> data = new MutableLiveData<>();

        this.apiService.getSpecie(specieId).enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<CharactersResponse> findCharacter(String character) {
        final MutableLiveData<com.popcode.starwars.data.remote.response.CharactersResponse> data = new MutableLiveData<>();

        this.apiService.findCharacter(character).enqueue(new Callback<com.popcode.starwars.data.remote.response.CharactersResponse>() {
            @Override
            public void onResponse(Call<com.popcode.starwars.data.remote.response.CharactersResponse> call, Response<com.popcode.starwars.data.remote.response.CharactersResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<com.popcode.starwars.data.remote.response.CharactersResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<CharacterElement> getCharacter(Integer id) {
        final MutableLiveData<CharacterElement> data = new MutableLiveData<>();

        this.apiService.getCharacter(id).enqueue(new Callback<CharacterElement>() {
            @Override
            public void onResponse(Call<CharacterElement> call, Response<CharacterElement> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CharacterElement> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}
