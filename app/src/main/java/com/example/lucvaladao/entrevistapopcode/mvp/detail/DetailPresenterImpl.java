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
    private Character character;

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
    public void getInfo(Character character) {
        this.character = character;
        detailInteractor.getSpecieInfo(getId(character.getSpecies().get(0)), this);
    }

    @Override
    public void onGetSpecieInfoSuccess(Specie specie) {
        detailView.setSpecieText(specie.getName());
        detailInteractor.getPlanetInfo(getId(character.getHomeworld()), this);
    }

    @Override
    public void onGetSpecieInfoFailure(String message) {
        detailView.showToast(message);
    }

    @Override
    public void onGetPlanetInfoSuccess(Planet planet) {
        detailView.setPlanetText(planet.getName());
    }

    @Override
    public void onGetPlanetInfoFailure(String message) {
        detailView.showToast(message);
    }

    public String getId (String info){
        int aux = info.length();
        return info.substring(aux - 2, aux - 1);
    }
}
