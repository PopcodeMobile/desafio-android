package com.example.starwarswiki.handlers;

import android.os.AsyncTask;

import com.example.starwarswiki.structural.People;
import com.example.starwarswiki.structural.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URL;

public class SWAPIHandler extends AsyncTask<String, Void, People> {
    private Person person;
    private People people;

    public interface MyCallbackInterface {
        public void onRequestCompleted(People result);
    }

    private MyCallbackInterface mCallback;

    public SWAPIHandler(MyCallbackInterface callback) {
        mCallback = callback;
    }

    @Override
    protected People doInBackground(String... strings) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
             people = objectMapper.readValue(new URL("https://swapi.co/api/people?format=json"), People.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return people;
    }

    @Override
    protected void onPostExecute(People people) {
        mCallback.onRequestCompleted(people);
    }
}
