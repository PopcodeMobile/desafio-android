package com.example.lucvaladao.entrevistapopcode.mvp.detail;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.lucvaladao.entrevistapopcode.db.MyApp;
import com.example.lucvaladao.entrevistapopcode.entity.Character;
import com.example.lucvaladao.entrevistapopcode.entity.FavResponse;
import com.example.lucvaladao.entrevistapopcode.entity.Planet;
import com.example.lucvaladao.entrevistapopcode.entity.Specie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lucvaladao on 3/20/18.
 */

class DetailInteractorImpl implements DetailInteractor {

    private Retrofit mRetrofit = new Retrofit
            .Builder()
            .baseUrl(DetailRetrofit.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private DetailRetrofit mRetrofitService = mRetrofit.create(DetailRetrofit.class);

    @Override
    public void postCharacterRemote(String id, String auxHeader, final PostCharacterRemoteListener listener) {
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(DetailRetrofit.URL_FAV)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DetailRetrofit retrofitService = retrofit.create(DetailRetrofit.class);

        retrofitService.postFavoriteCharacter(auxHeader, id).enqueue(new Callback<FavResponse>() {
            @Override
            public void onResponse(Call<FavResponse> call, Response<FavResponse> response) {
                if (response.code() == 201 && response.body() != null){
                    listener.onPostCharacterRemoteSuccess(response.body().getMessage());
                } else if (response.code() == 400 && response.body() != null){
                    listener.onPostCharacterRemoteFailure(response.body().getErrorMessage());
                } else {
                    listener.onPostCharacterRemoteFailure("Erro de comunicação com a API :( - onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<FavResponse> call, Throwable t) {
                listener.onPostCharacterRemoteFailure("Erro de comunicação com a API :( - onFailure");
            }
        });
    }

    @Override
    public void changeRequestStatus(Character character) {
        character.setRequestStatus(false);
        saveToDB(character);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void saveToDB(final Character character) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                MyApp.database.characterDAO().insertCharacter(character);
                return 1;
            }

            @Override
            protected void onPostExecute(Integer agentsCount) {}
        }.execute();
    }


    @Override
    public void getSpecieInfo(String id, final GetSpecieInfoListener listener) {
        mRetrofitService.getSpecieInfo(id).enqueue(new Callback<Specie>() {
            @Override
            public void onResponse(Call<Specie> call, Response<Specie> response) {
                if (response.code() == 200 & response.body() != null) {
                    listener.onGetSpecieInfoSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<Specie> call, Throwable t) {
                listener.onGetSpecieInfoFailure("Falha ao buscar informação de espécie!");
            }
        });
    }

    @Override
    public void getPlanetInfo(String id, final GetPlanetInfoListener listener) {
        mRetrofitService.getPlanetInfo(id).enqueue(new Callback<Planet>() {
            @Override
            public void onResponse(Call<Planet> call, Response<Planet> response) {
                if (response.code() == 200 && response.body() != null) {
                    listener.onGetPlanetInfoSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<Planet> call, Throwable t) {
                listener.onGetPlanetInfoFailure("Falha ao buscar informação do Planeta!");
            }
        });
    }
}
