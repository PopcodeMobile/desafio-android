package com.example.lucvaladao.entrevistapopcode.mvp.home;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lucvaladao on 3/19/18.
 */

class HomeInteractorImpl implements HomeInteractor {

    Retrofit mRetrofit = new Retrofit
            .Builder()
            .baseUrl(HomeRetrofit.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    HomeRetrofit mRetrofitService = mRetrofit.create(HomeRetrofit.class);

    @Override
    public void getCharacterList(GetCharacterListener listener) {
        mRetrofitService.getCharacterList().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
