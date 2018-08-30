package com.android.vferreirati.starwarscharacters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.vferreirati.starwarscharacters.models.Character;
import com.android.vferreirati.starwarscharacters.models.Planet;
import com.android.vferreirati.starwarscharacters.models.Specie;
import com.android.vferreirati.starwarscharacters.services.CharacterApi;
import com.android.vferreirati.starwarscharacters.services.PlanetService;
import com.android.vferreirati.starwarscharacters.services.SpecieService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_CHARACTER = "com.android.vferreirati.starwarscharacters.character";
    private static final String KEY_CHARACTER_PLANET = "CharacterPlanetKey";
    private static final String KEY_CHARACTER_SPECIE = "CharacterSpecieKey";
    private static final String TAG = "DetailActivity";
    private static final String SPLIT_WORD_SPECIE = "species/";
    private static final String SPLIT_WORD_PLANET = "planets/";

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

    private String mCharacterPlanetName;
    private String mCharacterSpecieName;

    // Extracted ID from Character HomeWorld URL
    private int mCharacterPlanetId;

    // Extracted ID from Character first Species URlL
    private int mCharacterSpecieId;

    // Retrofit PlanetService
    private PlanetService mPlanetService;

    // Retrofit SpecieService
    private SpecieService mSpecieService;

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
        mCharacterSpecieId = extractIdFromUrlString(mCharacter.getSpecies().get(0), SPLIT_WORD_SPECIE);
        mCharacterPlanetId = extractIdFromUrlString(mCharacter.getHomeworld(), SPLIT_WORD_PLANET);

        mCharacterNameTextView.setText(mCharacter.getName());
        mCharacterHeightTextView.setText(mCharacter.getHeight());
        mCharacterGenderTextView.setText(mCharacter.getGender());
        mCharacterWeightTextView.setText(mCharacter.getMass());
        mCharacterHairColorTextView.setText(mCharacter.getHairColor());
        mCharacterEyeColorTextView.setText(mCharacter.getEyeColor());
        mCharacterSkinColorTextView.setText(mCharacter.getSkinColor());
        mCharacterBirthYearTextView.setText(mCharacter.getBirthYear());

        mPlanetService = CharacterApi.getClient().create(PlanetService.class);
        mSpecieService = CharacterApi.getClient().create(SpecieService.class);

        if(savedInstanceState != null) {
            mCharacterSpecieName = savedInstanceState.getString(KEY_CHARACTER_SPECIE, "");
            mCharacterPlanetName = savedInstanceState.getString(KEY_CHARACTER_PLANET, "");
            mCharacterHomePlanetTextView.setText(mCharacterPlanetName);
            mCharacterSpecieTextView.setText(mCharacterSpecieName);

            Log.d(TAG, "Reusing character data!");
        } else {
            loadCharacterRemainingData();
        }
    }

    private void loadCharacterRemainingData() {
        callPlanetQueryApi().enqueue(new Callback<Planet>() {
            @Override
            public void onResponse(Call<Planet> call, Response<Planet> response) {
                mCharacterPlanetName = response.body().getName();
                mCharacterHomePlanetTextView.setText(mCharacterPlanetName);
            }

            @Override
            public void onFailure(Call<Planet> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "Failed while requesting planet data", Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.getMessage());
            }
        });

        callSpecieQueryApi().enqueue(new Callback<Specie>() {
            @Override
            public void onResponse(Call<Specie> call, Response<Specie> response) {
                mCharacterSpecieName = response.body().getName();
                mCharacterSpecieTextView.setText(mCharacterSpecieName);
            }

            @Override
            public void onFailure(Call<Specie> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "Failed while requesting specie data", Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private Call<Planet> callPlanetQueryApi() {
        return mPlanetService.getPlanet(
                mCharacterPlanetId,
                getString(R.string.query_format)
        );
    }

    private Call<Specie> callSpecieQueryApi() {
        return mSpecieService.getSpecie(
                mCharacterSpecieId,
                getString(R.string.query_format)
        );
    }

    public static Intent newIntent(Context packageContext, Character character) {
        Intent intent = new Intent(packageContext, DetailActivity.class);
        intent.putExtra(EXTRA_CHARACTER, character);

        return intent;
    }

    private int extractIdFromUrlString(String url, String splitWord) {
        String[] splitString = url.split(splitWord);
        String id = splitString[1].split("/")[0];
        return Integer.valueOf(id);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(mCharacterPlanetName != null) {
            outState.putString(KEY_CHARACTER_PLANET, mCharacterPlanetName);
        }

        if(mCharacterSpecieName != null) {
            outState.putString(KEY_CHARACTER_SPECIE, mCharacterSpecieName);
        }
    }
}
