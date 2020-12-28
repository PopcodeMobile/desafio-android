package com.example.desafiopopcode.Controllers;

import android.os.AsyncTask;

import com.example.desafiopopcode.Models.Personagem;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class BuscaPerson extends AsyncTask<Void, Void, Personagem> {

    private final String person;

    public BuscaPerson(String person) {
        this.person = person;
    }

    @Override
    protected Personagem doInBackground(Void... voids) {
        StringBuilder resposta = new StringBuilder();

        if (this.person != null && this.person.length() == 8) {
            try {
                URL url = new URL("https://https://swapi.dev/api/people/" + this.person + "/");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                connection.setConnectTimeout(5000);
                connection.connect();

                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    resposta.append(scanner.next());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new Gson().fromJson(resposta.toString(), Personagem.class);
    }
}