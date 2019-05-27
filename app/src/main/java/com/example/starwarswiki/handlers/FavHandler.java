package com.example.starwarswiki.handlers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.starwarswiki.structural.FavResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class FavHandler extends AsyncTask<Boolean, Void, String> {
    private FavCallBack favCallBack;

    public interface FavCallBack {
        public void onRequestCompleted(String result);
    }

    public FavHandler(FavCallBack callBack){ favCallBack = callBack; }

    @Override
    protected String doInBackground(Boolean... booleans) {
        String path = "https://private-anon-beba91a530-starwarsfavorites.apiary-mock.com/favorite/1701";
        OutputStream outputStream = null;
        String message = "";
        JsonObject jsonObject;

        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("content-type","application/json");
            if(booleans[0]){
                connection.setRequestProperty("Prefer","status=400");
            }
            connection.connect();
            InputStream stream = connection.getInputStream();
            int responseCode = connection.getResponseCode();

            ByteArrayOutputStream responseBody = new ByteArrayOutputStream();
            byte buffer[] = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = stream.read(buffer)) > 0) {
                responseBody.write(buffer, 0, bytesRead);
            }

            connection.disconnect();
            FavResponse response = new Gson().fromJson(responseBody.toString(), FavResponse.class);
            message = response.toString();
            return connection.getResponseMessage();
        } catch (Exception e) {
            e.getMessage();
        }
        return message;
    }

    @Override
    protected void onPostExecute(String result) {
        favCallBack.onRequestCompleted(result);
    }

}
