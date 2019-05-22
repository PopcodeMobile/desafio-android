package com.example.starwarswiki.handlers;

import android.os.AsyncTask;
import com.example.starwarswiki.structural.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URL;

public class SWAPIHandler extends AsyncTask<String, Void, Person> {
    private Person person;

    public interface MyCallbackInterface {
        public void onRequestCompleted(Person result);
    }

    private MyCallbackInterface mCallback;

    public SWAPIHandler(MyCallbackInterface callback) {
        mCallback = callback;
    }

    @Override
    protected Person doInBackground(String... strings) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            person = objectMapper.readValue(new URL("https://swapi.co/api/people/1/?format=json"), Person.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return person;
    }

    @Override
    protected void onPostExecute(Person person) {
        mCallback.onRequestCompleted(person);
    }
}
