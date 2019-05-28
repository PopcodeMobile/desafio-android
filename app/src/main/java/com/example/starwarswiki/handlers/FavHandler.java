package com.example.starwarswiki.handlers;

import android.os.AsyncTask;

import com.example.starwarswiki.structural.FavLogItem;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class FavHandler extends AsyncTask<String, Void, FavLogItem> {
    private FavCallBack favCallBack;

    public interface FavCallBack {
        public void onRequestCompleted(FavLogItem result);
    }

    public FavHandler(FavCallBack callBack){ favCallBack = callBack; }

    @Override
    protected FavLogItem doInBackground(String... strings) {
        // Let's not forget about Star Trek also
        // USS Enterprise (NCC 1701)
        String path = "https://private-anon-beba91a530-starwarsfavorites.apiary-mock.com/favorite/1701";
        Random r = new Random();
        Boolean willItFail = (r.nextInt(2) == 0) ? true : false;

        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("content-type","application/json");
            //Half of the submissions will fail
            if(willItFail){
                connection.setRequestProperty("Prefer","status=400");
            }
            connection.connect();
            InputStream stream;
            if(connection.getResponseCode() == 201) {
                stream = connection.getInputStream();
            } else {
                stream = connection.getErrorStream();
            }
            ByteArrayOutputStream responseBody = new ByteArrayOutputStream();
            byte buffer[] = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = stream.read(buffer)) > 0) {
                responseBody.write(buffer, 0, bytesRead);
            }

            connection.disconnect();
            FavLogItem response = new Gson().fromJson(responseBody.toString(), FavLogItem.class);
            response.setName(strings[0]);
            return response;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    protected void onPostExecute(FavLogItem result) {
        favCallBack.onRequestCompleted(result);
    }

}
