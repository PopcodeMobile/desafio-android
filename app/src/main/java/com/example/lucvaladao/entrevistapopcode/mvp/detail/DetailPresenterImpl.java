package com.example.lucvaladao.entrevistapopcode.mvp.detail;

import com.example.lucvaladao.entrevistapopcode.entity.Character;
import com.example.lucvaladao.entrevistapopcode.entity.Planet;
import com.example.lucvaladao.entrevistapopcode.entity.Specie;
import com.example.lucvaladao.entrevistapopcode.mvp.detail.DetailInteractor.GetFromDBListener;
import com.example.lucvaladao.entrevistapopcode.mvp.detail.DetailInteractor.GetPlanetInfoListener;
import com.example.lucvaladao.entrevistapopcode.mvp.detail.DetailInteractor.GetSpecieInfoListener;

import java.util.List;

/**
 * Created by lucvaladao on 3/20/18.
 */

class DetailPresenterImpl implements DetailPresenter, GetFromDBListener, GetSpecieInfoListener, GetPlanetInfoListener {
    @Override
    public void bindView(DetailView detailView) {

    }

    @Override
    public void unbindView() {

    }

    @Override
    public void putCharacterIntoFav(Character character) {

    }

    @Override
    public void removeCharacterFromFav(Character character) {

    }

    @Override
    public void onGetFromDBSuccess(List<Character> characterList) {

    }

    @Override
    public void onGetSpecieInfoSuccess(Specie specie) {

    }

    @Override
    public void onGetPlanetInfoSuccess(Planet planet) {

    }
}
