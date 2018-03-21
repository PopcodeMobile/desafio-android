package com.example.lucvaladao.entrevistapopcode.mvp.offline;

import com.example.lucvaladao.entrevistapopcode.entity.Character;
import com.example.lucvaladao.entrevistapopcode.entity.FavResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by lucvaladao on 3/20/18.
 */

interface OfflineInteractor {
    void putCHaracterIntoDB(Character character);

    interface PostCharacterRemoteListener {
        void onPostCharacterRemoteSuccess(Character character);
        void onPostCharacterRemoteFailure(Character character);
    }
    void postCharacterRemote (Character character, String id, String auxHeader, PostCharacterRemoteListener listener);

    interface OnConcludeListener {
        void onConclude(List<Character> characterList);
    }
    void getAllCharacterFailure(OnConcludeListener listener);
}

interface OfflineRetrofit {

    String URL_FAV = "http://private-782d3-starwarsfavorites.apiary-mock.com/";

    @POST("favorite/{id}")
    Call<FavResponse> postFavoriteCharacter(@Header("Prefer") String auxHeader, @Path("id") String id);

}

