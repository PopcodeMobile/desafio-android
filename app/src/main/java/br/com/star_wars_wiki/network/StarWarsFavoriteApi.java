package br.com.star_wars_wiki.network;

import br.com.star_wars_wiki.APIConstants;
import retrofit.RestAdapter;

public class StarWarsFavoriteApi {
    private StarWars mSwApi;
    private static StarWarsFavoriteApi sInstance;

    private StarWarsFavoriteApi(){
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(APIConstants.BASE_URL_FAVORITES)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        mSwApi = restAdapter.create(StarWars.class);
    }

    public static void init(){
        sInstance = new StarWarsFavoriteApi();
    }

    public static StarWars getApi(){
        return sInstance.mSwApi;
    }

    public void setApi(StarWars starWarsApi){
        sInstance.mSwApi = starWarsApi;
    }
}
