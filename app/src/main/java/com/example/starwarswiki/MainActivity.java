package com.example.starwarswiki;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starwarswiki.handlers.PeopleHandler;
import com.example.starwarswiki.handlers.PersonRepository;
import com.example.starwarswiki.structural.People;
import com.example.starwarswiki.structural.Person;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PeopleHandler.MyCallbackInterface {
    private PersonRepository personRepository;
    private LiveData<List<Person>> listOfPerson;
    private PersonViewModel mViewModel;
    public static final int NEW_PERSON_ACTIVITY_REQUEST_CODE = 1;

    private String status = "Loading...";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final PersonListAdapter adapter = new PersonListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new PeopleHandler(this).execute("https://swapi.co/api/people?format=json");

        mViewModel.getAllPerson().observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(List<Person> listOfPerson) {
                adapter.setListOfPersonPerson(listOfPerson);
            }
        });

        FloatingActionButton fab =findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, status, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//                Intent intent = new Intent(MainActivity.this, NewPersonActivity.class);
//                startActivityForResult(intent, NEW_PERSON_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    public void onRequestCompleted(People result) {
        status = "ready";
        if(result != null) {
            mViewModel.insert(result.getList());
        }
        if(result.getNext() != null) {
            new PeopleHandler(this).execute(result.getNext());
        }
    }
}
