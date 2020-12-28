package com.example.desafiopopcode.Controllers;

import retrofit.RestAdapter;

public class FavApi {

    private SWWiki fav;
    private static FavApi Instance;

    private FavApi() {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setClient(new SWClient())
                .setEndpoint("http://private-782d3-starwarsfavorites.apiary-mock.com/")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        fav = restAdapter.create(SWWiki.class);
    }

    public static void init() {
        Instance = new FavApi();
    }

    public static SWWiki getApi() {
        return Instance.fav;
    }

    public void setApi(SWWiki favApi) {
        Instance.fav = favApi;
    }
}