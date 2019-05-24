package com.example.starwarswiki.handlers;

import android.os.AsyncTask;

import com.example.starwarswiki.structural.People;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URL;

public class PeopleHandler extends AsyncTask<String, Void, People> {
    private People people;
    private MyCallbackInterface mCallback;

    public interface MyCallbackInterface {
        public void onRequestCompleted(People result);

    }

    public PeopleHandler(MyCallbackInterface callback) {
        mCallback = callback;
    }

    @Override
    protected People doInBackground(String... strings) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
             people = objectMapper.readValue(new URL(strings[0]), People.class);
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