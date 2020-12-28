package com.example.desafiopopcode.Controllers;

import android.os.Build;
import retrofit.client.OkClient;
import retrofit.client.Request;

import java.io.IOException;
import java.net.HttpURLConnection;

public class FavClient extends OkClient {

    public FavClient() {
        super();
    }

    @Override
    protected HttpURLConnection openConnection(Request request) throws IOException {
        HttpURLConnection connection = super.openConnection(request);
        connection.setRequestProperty("User-Agent", "starwarsfavorites-android-" + Build.VERSION.RELEASE);
        return connection;
    }
}
