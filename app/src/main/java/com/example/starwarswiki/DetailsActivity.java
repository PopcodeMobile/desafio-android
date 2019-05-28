package com.example.starwarswiki;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.starwarswiki.handlers.FavHandler;
import com.example.starwarswiki.structural.FavLogItem;
import com.example.starwarswiki.structural.Person;
import com.example.starwarswiki.structural.Planet;
import com.example.starwarswiki.structural.Specie;
import com.example.starwarswiki.view_models.DetailsViewModel;
import com.example.starwarswiki.view_models.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    private String json;
    private Person person;
    private DetailsViewModel detailsViewModel;
    private MainViewModel mainViewModel;
    private String homeworld = "Loading...";
    private String species = "Loading...";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView data;
        super.onCreate(savedInstanceState);

        json  = getIntent().getStringExtra("person");
        person = new Gson().fromJson(json, Person.class);

        detailsViewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);

        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(person.getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        detailsViewModel.queryPlanetByPath(person.getHomeworldURL()).observe(this, new Observer<List<Planet>>() {
            @Override
            public void onChanged(List<Planet> list) {
                if(list.size() > 0){
                    homeworld = list.get(0).getName();
                    TextView data = findViewById(R.id.dt_home_planet);
                    data.setText(homeworld);
                }
            }
        });

        if(person.getSpeciesURL().size() > 0) {
            detailsViewModel.querySpecieByList(person.getSpeciesURL()).observe(this, new Observer<List<Specie>>() {
                @Override
                public void onChanged(List<Specie> list) {
                    if(list.size() > 0) {
                        //Concatanating if theres more than one specie (who knows???)
                        for(int i = 0; i < list.size(); i++) {
                            if(i == 0){
                                species = list.get(i).getName();
                            } else {
                                species += ", " + list.get(i).getName();
                            }
                        }
                        TextView data = findViewById(R.id.dt_species);
                        data.setText(species);
                    }
                }
            });
        } else {
            species = getString(R.string.na);
        }

        data = findViewById(R.id.dt_height);
        data.setText(person.getHeight());

        data = findViewById(R.id.dt_mass);
        data.setText(person.getMass());

        data = findViewById(R.id.dt_birth_year);
        data.setText(person.getBirthYear());

        data = findViewById(R.id.dt_eye_color);
        data.setText(person.getEyeColor());

        data = findViewById(R.id.dt_hair_color);
        data.setText(person.getHairColor());

        data = findViewById(R.id.dt_skin_color);
        data.setText(person.getSkinColor());

        data = findViewById(R.id.dt_gender);
        data.setText(person.getGender());

        data = findViewById(R.id.dt_home_planet);
        data.setText(homeworld);

        data = findViewById(R.id.dt_species);
        data.setText(species);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setBackgroundTintList((person.getFavorite() == 1) ?
                ContextCompat.getColorStateList(this, R.color.fab_fav_on) :
                ContextCompat.getColorStateList(this, R.color.fab_fav_off));
    }
}
