package com.example.lucvaladao.entrevistapopcode.mvp.offline;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.lucvaladao.entrevistapopcode.db.MyApp;
import com.example.lucvaladao.entrevistapopcode.entity.Character;
import com.example.lucvaladao.entrevistapopcode.entity.FavResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.lucvaladao.entrevistapopcode.mvp.offline.OfflineRetrofit.URL_FAV;

/**
 * Created by lucvaladao on 3/20/18.
 */

class OfflineInteractorImpl implements OfflineInteractor {

    @SuppressLint("StaticFieldLeak")
    @Override
    public void putCHaracterIntoDB(final Character character) {
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
    public void postCharacterRemote(final Character character, String id, String auxHeader, final PostCharacterRemoteListener listener) {
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(URL_FAV)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OfflineRetrofit retrofitService = retrofit.create(OfflineRetrofit.class);

        retrofitService.postFavoriteCharacter(auxHeader, id).enqueue(new Callback<FavResponse>() {
            @Override
            public void onResponse(Call<FavResponse> call, Response<FavResponse> response) {
                if (response.code() == 201 && response.body() != null){
                    listener.onPostCharacterRemoteSuccess(character);
                } else if (response.code() == 400 && response.body() != null){
                    listener.onPostCharacterRemoteFailure(character);
                } else {
                    listener.onPostCharacterRemoteFailure(character);
                }
            }

            @Override
            public void onFailure(Call<FavResponse> call, Throwable t) {
                listener.onPostCharacterRemoteFailure(character);
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void getAllCharacterFailure(final OnConcludeListener listener) {
        new  AsyncTask<Void, Void, List<Character>>() {
            @Override
            protected List<Character> doInBackground(Void... params) {
                return MyApp.database.characterDAO().getCharactersFavoriteFailure(false);
            }

            @Override
            protected void onPostExecute(List<Character> characterList) {
                listener.onConclude(characterList);
            }
        }.execute();
    }
}
