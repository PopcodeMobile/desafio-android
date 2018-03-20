package com.example.lucvaladao.entrevistapopcode.mvp.detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lucvaladao.entrevistapopcode.R;
import com.example.lucvaladao.entrevistapopcode.entity.Character;

public class DetailActivity extends AppCompatActivity implements DetailView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    @Override
    public void putCharacterIntoFav(Character character) {

    }

    @Override
    public void removeCharacterFromFav(Character character) {

    }

    @Override
    public void showToast() {

    }

    @Override
    public void toggleFavButton() {

    }
}
