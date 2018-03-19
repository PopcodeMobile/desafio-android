package com.example.lucvaladao.entrevistapopcode.mvp.favorite;

import com.example.lucvaladao.entrevistapopcode.entity.Character;
import com.example.lucvaladao.entrevistapopcode.entity.CharacterBook;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lucvaladao on 3/19/18.
 */

interface FavoriteInteractor {
    interface GetCharacterFromDBListener {
        void onGetCharacterFromDBSuccess(List<Character> characterList);
    }
    void getCharacterFromDB (GetCharacterFromDBListener listener);
}

interface FavoritePresenter {
    void getCharacterList ();
    void getCharacterNextPage ();
    void bindView (FavoriteView favoriteView);
    void unbindView ();
}

interface FavoriteView {
    void showToast (String message);
    void fillAdapter (List<Character> characterList);
    void showProgress ();
    void hideProgress ();
    void showNoResults ();
    void hideNoResults ();
}

