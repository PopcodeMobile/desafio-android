package com.example.starwarswiki;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.starwarswiki.handlers.DataSanityHandler;
import com.example.starwarswiki.handlers.PeopleHandler;
import com.example.starwarswiki.handlers.PlanetsNameHandler;
import com.example.starwarswiki.structural.People;
import com.example.starwarswiki.structural.Person;
import com.example.starwarswiki.structural.Planet;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PeopleHandler.MyCallbackInterface, PlanetsNameHandler.MyCallbackInterface {
    private String status;
    private List<Person> people;

    //Indexes to mark itens already fetched
    private int fetchPlanetsIndex = 0;
    private int fetchSpeciesIndex = 0;
    private DataSanityHandler dataSanityHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab =findViewById(R.id.settings);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, status, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        dataSanityHandler = new DataSanityHandler(this.getApplicationContext());
    }

    @Override
    public void onRequestCompleted(People result) {

    }

    @Override
    public void onRequestCompleted(Planet result) {

    }
}
