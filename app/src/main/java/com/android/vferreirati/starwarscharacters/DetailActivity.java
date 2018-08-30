package com.android.vferreirati.starwarscharacters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.vferreirati.starwarscharacters.models.Character;

public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_CHARACTER = "com.android.vferreirati.starwarscharacters.character";
    private static final String TAG = "DetailActivity";

    private Character mCharacter;
    private TextView mCharacterNameTextView;
    private TextView mCharacterHeightTextView;
    private TextView mCharacterGenderTextView;
    private TextView mCharacterWeightTextView;
    private TextView mCharacterHairColorTextView;
    private TextView mCharacterSkinColorTextView;
    private TextView mCharacterEyeColorTextView;
    private TextView mCharacterBirthYearTextView;
    private TextView mCharacterHomePlanetTextView;
    private TextView mCharacterSpecieTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mCharacter = getIntent().getParcelableExtra(EXTRA_CHARACTER);
        mCharacterNameTextView = findViewById(R.id.tv_character_name);
        mCharacterHeightTextView = findViewById(R.id.tv_character_height);
        mCharacterGenderTextView = findViewById(R.id.tv_character_gender);
        mCharacterWeightTextView = findViewById(R.id.tv_character_mass);
        mCharacterHairColorTextView = findViewById(R.id.tv_character_hair_color);
        mCharacterSkinColorTextView = findViewById(R.id.tv_character_skin_color);
        mCharacterEyeColorTextView = findViewById(R.id.tv_character_eye_color);
        mCharacterBirthYearTextView = findViewById(R.id.tv_character_birth_year);
        mCharacterHomePlanetTextView = findViewById(R.id.tv_character_home_planet);
        mCharacterSpecieTextView = findViewById(R.id.tv_character_specie);

        mCharacterNameTextView.setText(mCharacter.getName());
        mCharacterHeightTextView.setText(mCharacter.getHeight());
        mCharacterGenderTextView.setText(mCharacter.getGender());
        mCharacterWeightTextView.setText(mCharacter.getMass());
        mCharacterHairColorTextView.setText(mCharacter.getHairColor());
        mCharacterEyeColorTextView.setText(mCharacter.getEyeColor());
        mCharacterSkinColorTextView.setText(mCharacter.getSkinColor());
        mCharacterBirthYearTextView.setText(mCharacter.getBirthYear());

        // TODO: Update this when making request to swapi
        mCharacterHomePlanetTextView.setText("Earth");
        mCharacterSpecieTextView.setText("Human");
    }

    public static Intent newIntent(Context packageContext, Character character) {
        Intent intent = new Intent(packageContext, DetailActivity.class);
        intent.putExtra(EXTRA_CHARACTER, character);

        return intent;
    }
}
