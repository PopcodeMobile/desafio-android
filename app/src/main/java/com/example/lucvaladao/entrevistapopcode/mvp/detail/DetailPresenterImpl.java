package com.example.lucvaladao.entrevistapopcode.mvp.detail;

import com.example.lucvaladao.entrevistapopcode.entity.Character;
import com.example.lucvaladao.entrevistapopcode.entity.Planet;
import com.example.lucvaladao.entrevistapopcode.entity.Specie;
import com.example.lucvaladao.entrevistapopcode.mvp.detail.DetailInteractor.GetPlanetInfoListener;
import com.example.lucvaladao.entrevistapopcode.mvp.detail.DetailInteractor.GetSpecieInfoListener;

import java.util.List;

/**
 * Created by lucvaladao on 3/20/18.
 */

class DetailPresenterImpl implements DetailPresenter, GetSpecieInfoListener, GetPlanetInfoListener {

    private DetailInteractor detailInteractor;
    private DetailView detailView;

    DetailPresenterImpl (DetailInteractor detailInteractor, DetailView detailView){
        this.detailInteractor = detailInteractor;
        this.detailView = detailView;
    }

    @Override
    public void bindView(DetailView detailView) {
        this.detailView = detailView;
    }

    @Override
    public void unbindView() {
        this.detailView = null;
    }

    @Override
    public void putCharacterIntoFav(Character character) {
        character.setFav(true);
        detailInteractor.saveToDB(character);
    }

    @Override
    public void removeCharacterFromFav(Character character) {
        character.setFav(false);
        detailInteractor.saveToDB(character);
    }

    @Override
    public void onGetSpecieInfoSuccess(Specie specie) {
        detailView.setSpecieText(specie.)
    }

    @Override
    public void onGetSpecieInfoFailure(String message) {

    }

    @Override
    public void onGetPlanetInfoSuccess(Planet planet) {

    }

    @Override
    public void onGetPlanetInfoFailure(String message) {

    }
}
