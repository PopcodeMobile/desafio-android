package com.example.desafiopopcode.Controllers;

import retrofit.RestAdapter;

public class SWApi {

    private SWWiki sw;
    private static SWApi Instance;

    private SWApi() {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setClient(new SWClient())
                .setEndpoint("https://swapi.dev/api/")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        sw = restAdapter.create(SWWiki.class);
    }

    public static void init() {
        Instance = new SWApi();
    }

    public static SWWiki getApi() {
        return Instance.sw;
    }

    public void setApi(SWWiki swApi) {
        Instance.sw = swApi;
    }
}