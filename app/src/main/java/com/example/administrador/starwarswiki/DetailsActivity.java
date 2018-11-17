package com.example.administrador.starwarswiki;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);
        DetailsViewModel detailsViewModel = new DetailsViewModel(getApplication(),id );
        View view =  this.findViewById(R.id.activity_details);

        ToggleButton favbtn =  view.findViewById(R.id.details_favorite_button);

        TextView textViewName = view.findViewById(R.id.details_name);
        TextView textViewGender = view.findViewById(R.id.details_gender);
        TextView textViewHeight = view.findViewById(R.id.details_height);
        TextView textViewMass = view.findViewById(R.id.details_mass);
        TextView textViewEye = view.findViewById(R.id.details_eye_color);
        TextView textViewHairColor = view.findViewById(R.id.details_hair_color);
        TextView textViewSkinColor = view.findViewById(R.id.details_skin_color);
        TextView textViewBirthYear = view.findViewById(R.id.details_birth_year);
        TextView textViewBirthPlace = view.findViewById(R.id.details_birth_place);
        TextView textViewSpecies = view.findViewById(R.id.details_species);

        detailsViewModel.getCharacter().observe(this, new Observer<StarWarsCharacter>() {
            @Override
            public void onChanged(@Nullable StarWarsCharacter starWarsCharacter) {
                textViewName.setText(starWarsCharacter.getName());
                textViewGender.setText(starWarsCharacter.getGender());
                textViewHeight.setText(starWarsCharacter.getHeight());
                textViewMass.setText(starWarsCharacter.getMass());
                textViewEye.setText(starWarsCharacter.getEye_color());
                textViewHairColor.setText(starWarsCharacter.getHair_color());
                textViewSkinColor.setText(starWarsCharacter.getSkin_color());
                textViewBirthYear.setText(starWarsCharacter.getBirth_year());
            }
        });

        favbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favbtn.isChecked()) {
                    // The toggle is enabled
                    Log.d(">>>>>>>>>", "inserindo favorito" + id);
                    detailsViewModel.updateFavorite(true, id);
                } else {
                    // The toggle is disabled
                    Log.d(">>>>>>>>>", "removendo favorito");
                    detailsViewModel.updateFavorite(false, id);
                }
            }
        });
    }
}
