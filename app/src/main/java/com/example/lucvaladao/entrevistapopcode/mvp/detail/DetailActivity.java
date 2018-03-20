package com.example.lucvaladao.entrevistapopcode.mvp.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lucvaladao.entrevistapopcode.R;
import com.example.lucvaladao.entrevistapopcode.entity.Character;
import com.example.lucvaladao.entrevistapopcode.mvp.detail.DetailPresenter.FavoriteActionListener;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

public class DetailActivity extends AppCompatActivity implements DetailView, FavoriteActionListener {

    TextView nameTextView, heightTextView, massTextView, hairColorTextView, skinColorTextView,
                eyeColorTextView, birthYearTextView, genderTextView, planetTextView, specieTextView;

    Toolbar toolbar;

    MaterialFavoriteButton materialFavoriteButton;

    private DetailPresenter mDetailPresenter = new DetailPresenterImpl(
            new DetailInteractorImpl(), this) {
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Init
        nameTextView = findViewById(R.id.textViewNameDetail);
        heightTextView = findViewById(R.id.textViewHeightDetail);
        massTextView = findViewById(R.id.textViewMassDetail);
        hairColorTextView = findViewById(R.id.textViewHairColorDetail);
        skinColorTextView = findViewById(R.id.textViewSkinColorDetail);
        eyeColorTextView = findViewById(R.id.textViewEyeColorDetail);
        birthYearTextView = findViewById(R.id.textViewBirthYearDetail);
        genderTextView = findViewById(R.id.textViewGenderDetail);
        planetTextView = findViewById(R.id.textViewPlanetDetail);
        specieTextView = findViewById(R.id.textViewSpecieDetail);
        materialFavoriteButton = findViewById(R.id.starButton);
        toolbar = findViewById(R.id.detailToolbar);

        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        final Character character = (Character) intent.getSerializableExtra("character");

        //Set
        nameTextView.setText(character.getName());
        heightTextView.setText(character.getHeight());
        massTextView.setText(character.getMass());
        hairColorTextView.setText(character.getHairColor());
        skinColorTextView.setText(character.getSkinColor());
        eyeColorTextView.setText(character.getEyeColor());
        birthYearTextView.setText(character.getBirthYear());
        genderTextView.setText(character.getGender());

        if (character.isFav()){
            materialFavoriteButton.setFavorite(true);
        } else {
            materialFavoriteButton.setFavorite(false);
        }

        materialFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (materialFavoriteButton.isFavorite()){
                    removeCharacterFromFav(character);
                } else {
                    putCharacterIntoFav(character);
                }
            }
        });
    }

    @Override
    public void putCharacterIntoFav(Character character) {
        mDetailPresenter.putCharacterIntoFav(character, this);
    }

    @Override
    public void removeCharacterFromFav(Character character) {
        mDetailPresenter.removeCharacterFromFav(character, this);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toggleFavButton() {
        materialFavoriteButton.toggleFavorite();
    }

    @Override
    public void setSpecieText(String specieName) {
        specieTextView.setText(specieName);
    }

    @Override
    public void setPlanetText(String planetName) {
        planetTextView.setText(planetName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        } else {
            super.onOptionsItemSelected(item);
            return false;
        }
    }

    @Override
    public void toggleFav() {
        materialFavoriteButton.toggleFavorite();
    }
}
