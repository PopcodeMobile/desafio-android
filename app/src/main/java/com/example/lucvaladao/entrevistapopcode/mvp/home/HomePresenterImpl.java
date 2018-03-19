package com.example.lucvaladao.entrevistapopcode.mvp.home;

import com.example.lucvaladao.entrevistapopcode.entity.Character;

import java.util.ArrayList;
import java.util.List;

import static com.example.lucvaladao.entrevistapopcode.mvp.home.HomeInteractor.*;

/**
 * Created by lucvaladao on 3/19/18.
 */

class HomePresenterImpl implements HomePresenter, GetCharacterListListener, GetCharacterNextPageListener {

    private HomeInteractor homeInteractor;
    private HomeView homeView;
    private List<Character> characterList;
    private String controleFluxo;

    public HomePresenterImpl(HomeInteractor homeInteractor, HomeView homeView){
        this.homeInteractor = homeInteractor;
        this.homeView = homeView;
        this.characterList = new ArrayList<>();
    }

    @Override
    public void getCharacterList() {
        homeInteractor.getCharacterList(this);
    }

    @Override
    public void getCharacterNextPage() {
        homeInteractor.getCharacterNextPage(characterList, controleFluxo, this);
    }

    @Override
    public void bindView(HomeView homeView) {
        this.homeView = homeView;
    }

    @Override
    public void unbindView() {
        this.homeView = null;
    }

    @Override
    public void onGetCharacterListSuccess(List<Character> characterList, String controleFluxo) {
        this.characterList = characterList;
        this.controleFluxo = controleFluxo;
    }

    @Override
    public void onGetCharacterListFailure(Exception e) {
        homeView.showToast("Erro ao carregar - FIRST PAGE!");
    }

    @Override
    public void onGetCharacterNextPageSuccess(List<Character> characterList, String controleFluxo) {
        this.characterList = characterList;
        this.controleFluxo = controleFluxo;
    }

    @Override
    public void onGetCharacterNextPageFailure(Exception e) {
        homeView.showToast("Erro ao carregar - NEXT PAGE!");
    }
}
