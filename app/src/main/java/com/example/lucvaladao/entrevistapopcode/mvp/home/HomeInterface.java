package com.example.lucvaladao.entrevistapopcode.mvp.home;

import com.example.lucvaladao.entrevistapopcode.entity.Character;
import com.example.lucvaladao.entrevistapopcode.entity.CharacterBook;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by lucvaladao on 3/19/18.
 */

interface HomeInteractor {
    interface GetCharacterListListener {
        void onGetCharacterListSuccess(List<Character> characterList, String controleFluxo, int comparedValue);
        void onGetCharacterListFailure(Exception e);
    }

    void getCharacterList (GetCharacterListListener listener);

    interface GetCharacterNextPageListener {
        void onGetCharacterNextPageSuccess (List<Character> characterList, String controleFluxo, int comparedValue);
        void onGetCharacterNextPageFailure (Exception e);
    }

    void getCharacterNextPage (List<Character> characterList, String controleFluxo, GetCharacterNextPageListener listener);
}

interface HomePresenter {
    void getCharacterList ();
    void getCharacterNextPage ();
    void bindView (HomeView homeView);
    void unbindView ();
}

interface HomeView {
    void showToast (String message);
    void fillAdapter (List<Character> characterList);
    void showProgress ();
    void hideProgress ();
    void showNoResults ();
    void hideNoResults ();
}

interface HomeRetrofit {
    String BASE_URL = "https://swapi.co/api/";

    @GET("people/")
    Call<CharacterBook> getCharacterList();

    @GET("people/{page}")
    Call<CharacterBook> getCharacterListNextPage(@Query("page") String page);
}

interface HomeAdapterInterface {
    void goToCharacterDetail(Character character);
}
