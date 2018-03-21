package com.example.lucvaladao.entrevistapopcode.mvp.favorite;

import com.example.lucvaladao.entrevistapopcode.entity.Character;

import java.util.List;

import static com.example.lucvaladao.entrevistapopcode.mvp.favorite.FavoriteInteractor.*;

/**
 * Created by lucvaladao on 3/19/18.
 */

class FavoritePresenterImpl implements FavoritePresenter, GetCharacterFromDBListener {

    private FavoriteInteractor favoriteInteractor;
    private FavoriteView favoriteView;
    private List<Character> characterList;

    public FavoritePresenterImpl(FavoriteInteractor favoriteInteractor, FavoriteView favoriteView) {
        this.favoriteInteractor = favoriteInteractor;
        this.favoriteView = favoriteView;
    }

    @Override
    public void getCharacterList() {
        favoriteInteractor.getCharacterFromDB(this);
    }

    @Override
    public void bindView(FavoriteView favoriteView) {
        this.favoriteView = favoriteView;
    }

    @Override
    public void unbindView() {
        this.favoriteView = null;
    }

    @Override
    public void onGetCharacterFromDBSuccess(List<Character> characterList) {
        favoriteView.fillAdapter(characterList);
    }
}
