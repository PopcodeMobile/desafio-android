package com.popcode.starwars.di;

import android.arch.lifecycle.ViewModelProvider;

import com.popcode.starwars.data.remote.ApiConstants;
import com.popcode.starwars.data.remote.ApiService;
import com.popcode.starwars.viewmodel.AppViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(subcomponents = ViewModelSubComponent.class)
class AppModule {
    @Singleton
    @Provides
    ApiService provideApiService() {
        return new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class);
    }

    @Singleton
    @Provides
    ViewModelProvider.Factory provideViewModelFactory(
            ViewModelSubComponent.Builder viewModelSubComponent) {

        return new AppViewModelFactory(viewModelSubComponent.build());
    }
}
