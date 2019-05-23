package com.example.starwarswiki.handlers;

import android.os.AsyncTask;

import com.example.starwarswiki.structural.Planet;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

public class PlanetNameHandler extends AsyncTask<String, Void, String> {
    private PlanetNameHandler.MyCallbackInterface mCallback;

    public interface MyCallbackInterface {
        public void onRequestCompleted(String result);
    }

    public PlanetNameHandler (PlanetNameHandler.MyCallbackInterface callback) {
        mCallback = callback;
    }

    @Override
    protected String doInBackground(String... strings) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new URL(strings[0]), Planet.class).getName();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String name){
        mCallback.onRequestCompleted(name);
    }
}
