package com.example.starwarswiki;

import com.example.starwarswiki.handlers.AppDatabase;
import com.example.starwarswiki.handlers.PeopleDAO;
import com.example.starwarswiki.handlers.PersonRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;


import android.app.Application;
import android.os.Bundle;
import android.view.View;

import com.example.starwarswiki.handlers.DataSanityHandler;
import com.example.starwarswiki.handlers.PeopleHandler;
import com.example.starwarswiki.handlers.PlanetsNameHandler;
import com.example.starwarswiki.structural.People;
import com.example.starwarswiki.structural.Person;
import com.example.starwarswiki.structural.Planet;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private PersonRepository personRepository;
    private LiveData<List<Person>> listOfPerson;
    private ViewModel mViewModel;

    private String status = "Loading...";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab =findViewById(R.id.settings);
        mViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, status, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
