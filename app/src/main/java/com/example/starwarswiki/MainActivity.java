package com.example.starwarswiki;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.starwarswiki.handlers.PeopleHandler;
import com.example.starwarswiki.handlers.PlanetNameHandler;
import com.example.starwarswiki.structural.People;
import com.example.starwarswiki.structural.Person;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PeopleHandler.MyCallbackInterface, PlanetNameHandler.MyCallbackInterface {
    private String status;
    private List<Person> people;

    //Indexes to mark itens already fetched
    private int fetchPlanetsIndex = 0;
    private int fetchSpeciesIndex = 0;

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

        new PeopleHandler(this).execute("https://swapi.co/api/people?format=json");
    }


    @Override
    public void onRequestCompleted(People result) {
        if(result.getPrevious() == null) {
            people = result.getResults();
            new PeopleHandler(this).execute(result.getNext());
            new PlanetNameHandler(this).execute(people.get(fetchPlanetsIndex).getHomeworldURL()+"?format=json");
        } else if (result.getNext() != null) {
            people.addAll(result.getResults());
            new PeopleHandler(this).execute(result.getNext());
        } else {
            people.addAll(result.getResults());
        }



        status = ""+people.size()+" /sp " +people.get(0).getHomewolrd();
    }

    @Override
    public void onRequestCompleted(String result) {
        people.get(fetchPlanetsIndex).setHomeworld(result);
        if (fetchPlanetsIndex < people.size()) {
            fetchPlanetsIndex++;
            new PlanetNameHandler(this).execute(people.get(fetchPlanetsIndex).getHomeworldURL()+"?format=json");
            Toast.makeText(this, people.get(fetchPlanetsIndex - 1).getHomewolrd() + " " + fetchPlanetsIndex, Toast.LENGTH_LONG).show();
        }
    }
}
