package com.example.starwarswiki.handlers;

import android.os.AsyncTask;

import com.example.starwarswiki.structural.Planet;
import com.example.starwarswiki.structural.Planets;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

public class PlanetsNameHandler extends AsyncTask<String, Void, Planets> {
    private Planets planets;
    private PlanetsNameHandler.MyCallbackInterface mCallback;

    public interface MyCallbackInterface {
        public void onRequestCompleted(Planets result);
    }

    public PlanetsNameHandler(PlanetsNameHandler.MyCallbackInterface callback) {
        mCallback = callback;
    }

    @Override
    protected Planets doInBackground(String... strings) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            planets = objectMapper.readValue(new URL(strings[0]), Planets.class);
            return planets;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Planets planets){
        mCallback.onRequestCompleted(planets);
    }
}
