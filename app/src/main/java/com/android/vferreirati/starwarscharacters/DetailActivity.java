package com.android.vferreirati.starwarscharacters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.vferreirati.starwarscharacters.models.Character;

public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_CHARACTER = "com.android.vferreirati.starwarscharacters.character";
    private static final String TAG = "DetailActivity";

    private Character mCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mCharacter = getIntent().getParcelableExtra(EXTRA_CHARACTER);
        Log.d(TAG, "Got character: " + mCharacter.getName());
    }

    public static Intent newIntent(Context packageContext, Character character) {
        Intent intent = new Intent(packageContext, DetailActivity.class);
        intent.putExtra(EXTRA_CHARACTER, character);

        return intent;
    }
}
