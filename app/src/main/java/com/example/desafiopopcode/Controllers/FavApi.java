package com.example.desafiopopcode.Controllers;

import retrofit.RestAdapter;

public class FavApi {

    private FavController fav;
    private static FavApi Instance;

    private FavApi() {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setClient(new SWClient())
                .setEndpoint("https://private-782d3-starwarsfavorites.apiary-mock.com/")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        fav = restAdapter.create(FavController.class);
    }

    public static void init() {
        Instance = new FavApi();
    }

    public static FavController getApi() {
        return Instance.fav;
    }

    public void setApi(FavController favApi) {
        Instance.fav = favApi;
    }
}