package br.com.star_wars_wiki.network;

import android.os.Build;
import retrofit.client.OkClient;
import retrofit.client.Request;

import java.io.IOException;
import java.net.HttpURLConnection;

public class StarWarsOkClient extends OkClient {

    public StarWarsOkClient() {
        super();
    }

    @Override
    protected HttpURLConnection openConnection(Request request) throws IOException {
        HttpURLConnection connection = super.openConnection(request);
        connection.setRequestProperty("User-Agent", "swapi-android-" + Build.VERSION.RELEASE);
        return connection;
    }
}