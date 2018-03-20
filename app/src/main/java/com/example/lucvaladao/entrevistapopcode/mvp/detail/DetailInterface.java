package com.example.lucvaladao.entrevistapopcode.mvp.detail;

import com.example.lucvaladao.entrevistapopcode.entity.Character;
import com.example.lucvaladao.entrevistapopcode.entity.CharacterBook;
import com.example.lucvaladao.entrevistapopcode.entity.Planet;
import com.example.lucvaladao.entrevistapopcode.entity.Specie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by lucvaladao on 3/20/18.
 */

interface DetailInteractor {

    void saveToDB (Character character);

    interface GetSpecieInfoListener {
        void onGetSpecieInfoSuccess (Specie specie);
        void onGetSpecieInfoFailure (String message);
    }
    void getSpecieInfo (String id, GetSpecieInfoListener listener);

    interface GetPlanetInfoListener {
        void onGetPlanetInfoSuccess (Planet planet);
        void onGetPlanetInfoFailure (String message);
    }
    void getPlanetInfo (String id, GetPlanetInfoListener listener);

}

interface DetailPresenter {

    interface FavoriteActionListener{
        void toggleFav();
    }

    void bindView (DetailView detailView);
    void unbindView ();
    void putCharacterIntoFav (Character character);
    void removeCharacterFromFav (Character character);
}

interface DetailView {
    void putCharacterIntoFav (Character character);
    void removeCharacterFromFav (Character character);
    void showToast ();
    void toggleFavButton ();
    void setSpecieText();
}

interface DetailRetrofit {
    String BASE_URL = "https://swapi.co/api/";

    @GET("planets/{id}")
    Call<Planet> getPlanetInfo(@Path("id") String id);

    @GET("species/{id}")
    Call<Specie> getSpecieInfo(@Path("id") String id);
}
