package com.example.starwarswiki;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.starwarswiki.handlers.SWAPIHandler;
import com.example.starwarswiki.structural.People;
import com.example.starwarswiki.structural.Person;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SWAPIHandler.MyCallbackInterface {
    private String status;
    private SWAPIHandler swapiHandler;

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

        swapiHandler = new SWAPIHandler(this);
        swapiHandler.execute();
    }


    @Override
    public void onRequestCompleted(People result) {
        status = ""+result.getResults().size();
    }
}
