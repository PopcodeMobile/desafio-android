package com.example.starwarswiki;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.starwarswiki.structural.Person;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

public class DetailsActivity extends AppCompatActivity {
    private String json;
    private Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView data;
        super.onCreate(savedInstanceState);

        json  = getIntent().getStringExtra("person");
        person = new Gson().fromJson(json, Person.class);

        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(person.getName());
        setSupportActionBar(toolbar);

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
        data.setText(person.getHomeworldURL());

        data = findViewById(R.id.dt_species);
        data.setText(person.getSpeciesURL().toString());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
