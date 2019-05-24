package com.example.starwarswiki.handlers;

import android.os.AsyncTask;

import com.example.starwarswiki.structural.Planet;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

public class PlanetsNameHandler extends AsyncTask<String, Void, Planet> {
    private PlanetsNameHandler.MyCallbackInterface mCallback;

    public interface MyCallbackInterface {
        public void onRequestCompleted(Planet result);
    }

    public PlanetsNameHandler(PlanetsNameHandler.MyCallbackInterface callback) {
        mCallback = callback;
    }

    @Override
    protected Planet doInBackground(String... strings) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new URL(strings[0]), Planet.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Planet name){
        mCallback.onRequestCompleted(name);
    }
}
