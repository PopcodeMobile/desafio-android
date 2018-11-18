package com.example.administrador.starwarswiki.ui.CharacterDetails;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrador.starwarswiki.R;
import com.example.administrador.starwarswiki.data.model.StarWarsCharacter;

public class CharacterDetailsActivity extends AppCompatActivity {
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);
        CharacterDetailsViewModel characterDetailsViewModel = new CharacterDetailsViewModel(getApplication(),id );
        View view =  this.findViewById(R.id.activity_details);

        FloatingActionButton favbtn =  view.findViewById(R.id.floatingActionButton);

        //TextView textViewName = view.findViewById(R.id.details_name);
        TextView textViewGender = view.findViewById(R.id.details_gender);
        TextView textViewHeight = view.findViewById(R.id.details_height);
        TextView textViewMass = view.findViewById(R.id.details_mass);
        TextView textViewEye = view.findViewById(R.id.details_eye_color);
        TextView textViewHairColor = view.findViewById(R.id.details_hair_color);
        TextView textViewSkinColor = view.findViewById(R.id.details_skin_color);
        TextView textViewBirthYear = view.findViewById(R.id.details_birth_year);
        TextView textViewBirthPlace = view.findViewById(R.id.details_birth_place);
        TextView textViewSpecies = view.findViewById(R.id.details_species);

        characterDetailsViewModel.getCharacter().observe(this, new Observer<StarWarsCharacter>() {
            @Override
            public void onChanged(@Nullable StarWarsCharacter starWarsCharacter) {
//                textViewName.setText(starWarsCharacter.getName());
                getSupportActionBar().setTitle(starWarsCharacter.getName());
                textViewGender.setText(starWarsCharacter.getGender());
                textViewHeight.setText(starWarsCharacter.getHeight());
                textViewMass.setText(starWarsCharacter.getMass());
                textViewEye.setText(starWarsCharacter.getEye_color());
                textViewHairColor.setText(starWarsCharacter.getHair_color());
                textViewSkinColor.setText(starWarsCharacter.getSkin_color());
                textViewBirthYear.setText(starWarsCharacter.getBirth_year());
                if(starWarsCharacter.isFavorite()){
                    flag = starWarsCharacter.isFavorite();
                    favbtn.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_on));
                }
                //favbtn.setChecked(starWarsCharacter.isFavorite());
                characterDetailsViewModel.getPlanetAndSpecies(
                        Integer.valueOf(starWarsCharacter.getHomeworld().replaceAll("[^\\d]", "")),
                        Integer.valueOf(starWarsCharacter.getSpecies().get(0).replaceAll("[^\\d]", ""))
                );
            }
        });

        characterDetailsViewModel.getPlanet().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textViewBirthPlace.setText(s);
            }
        });

        characterDetailsViewModel.getSpecie().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textViewSpecies.setText(s);
            }
        });


        favbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag) {
                    Log.d(">>>>>>>>>", "inserindo favorito" + id);
                    characterDetailsViewModel.updateFavorite(true, id);
                    favbtn.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_on));
                    flag = true;
                } else {
                    Log.d(">>>>>>>>>", "removendo favorito");
                    characterDetailsViewModel.updateFavorite(false, id);
                    favbtn.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_off));
                    flag = false;
                }
            }
        });
    }
}
