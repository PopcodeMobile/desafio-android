package com.example.lucvaladao.entrevistapopcode.mvp.home;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by lucvaladao on 3/19/18.
 */

interface HomeInteractor {
    interface GetCharacterListener {
        void onGetCharacterListenerSuccess (List<Character> characterList);
        void onGetCharacterListenerFailure (Exception e);
    }

    void getCharacterList (GetCharacterListener listener);
}

interface HomePresenter {
    void getCharacterList ();
    void bindView ();
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

    @GET("people")
    Call<String> getCharacterList();
}
