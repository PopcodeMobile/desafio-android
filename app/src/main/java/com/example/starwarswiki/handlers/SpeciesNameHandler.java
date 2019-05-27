package com.example.starwarswiki.handlers;

import android.os.AsyncTask;

import com.example.starwarswiki.structural.Species;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;


/**
 * Fetchs species page on AsyncTask
 */
public class SpeciesNameHandler extends AsyncTask<String, Void, Species> {
    private Species species;
    private SpeciesNameHandler.MyCallbackInterface mCallback;

    public interface MyCallbackInterface {
        public void onRequestCompleted(Species result);
    }

    public SpeciesNameHandler(SpeciesNameHandler.MyCallbackInterface callback) {
        mCallback = callback;
    }

    @Override
    protected Species doInBackground(String... strings) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            species = objectMapper.readValue(new URL(strings[0]), Species.class);
            return species;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Species species){
        mCallback.onRequestCompleted(species);
    }
}
