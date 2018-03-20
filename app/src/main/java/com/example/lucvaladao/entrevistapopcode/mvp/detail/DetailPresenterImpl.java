package com.example.lucvaladao.entrevistapopcode.mvp.detail;

import com.example.lucvaladao.entrevistapopcode.entity.Character;
import com.example.lucvaladao.entrevistapopcode.entity.Planet;
import com.example.lucvaladao.entrevistapopcode.entity.Specie;
import com.example.lucvaladao.entrevistapopcode.mvp.detail.DetailInteractor.GetPlanetInfoListener;
import com.example.lucvaladao.entrevistapopcode.mvp.detail.DetailInteractor.GetSpecieInfoListener;

import java.util.List;
import java.util.Random;

import static com.example.lucvaladao.entrevistapopcode.mvp.detail.DetailInteractor.*;

/**
 * Created by lucvaladao on 3/20/18.
 */

class DetailPresenterImpl implements DetailPresenter, GetSpecieInfoListener, GetPlanetInfoListener, PostCharacterRemoteListener {

    private DetailInteractor detailInteractor;
    private DetailView detailView;
    private Character character;

    DetailPresenterImpl (DetailInteractor detailInteractor, DetailView detailView){
        this.detailInteractor = detailInteractor;
        this.detailView = detailView;
    }


    @Override
    public void postCharacterRemote(Character character) {
        String auxHeader = Math.random() < 0.5 ? "400" : "201";
        detailInteractor.postCharacterRemote(String.valueOf(new Random().nextInt(100) + 1), auxHeader, this);
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
    public void putCharacterIntoFav(Character character, FavoriteActionListener listener) {
        character.setFav(true);
        listener.toggleFav();
        postCharacterRemote(character);
        detailInteractor.saveToDB(character);
    }

    @Override
    public void removeCharacterFromFav(Character character, FavoriteActionListener listener) {
        character.setFav(false);
        listener.toggleFav();
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

    private String getId(String info){
        int aux = info.length();
        return info.substring(aux - 2, aux - 1);
    }

    @Override
    public void onPostCharacterRemoteSuccess(String message) {
        detailView.showToast(message);
    }

    @Override
    public void onPostCharacterRemoteFailure(String message) {
        detailView.showToast(message);

    }
}
