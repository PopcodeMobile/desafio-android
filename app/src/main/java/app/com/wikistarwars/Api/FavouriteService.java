package app.com.wikistarwars.Api;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import android.os.Handler;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import app.com.wikistarwars.Model.Personagem;
import app.com.wikistarwars.Model.PersonagemRealm;
import io.realm.RealmResults;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;


public class FavouriteService {
    OkHttpClient client;
    Request request;
    String ROOT_URL = "https://private-anon-7d1a870a69-starwarsfavorites.apiary-mock.com/favorite/";
    Context context;
    SharedPreferences sp;
    String app_name;

    public FavouriteService(Context context) {
        if (client == null)
            client = new OkHttpClient();
        request = new Request.Builder()
                .url(ROOT_URL)
                .build();
        context = context;
        app_name = context.getPackageName();
        sp = context.getSharedPreferences(app_name, MODE_PRIVATE);
    }


        public void addFavourite(RealmResults<Personagem> personagens, final Context context) throws IOException {

        boolean prefer400 = sp.getBoolean("Prefer400", false);
        sp.edit().putBoolean("Prefer400", !prefer400).apply();
        Log.d(app_name,sp.getBoolean("Prefer400", false)+"");
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        HttpUrl.Builder urlBuilder = HttpUrl.parse(ROOT_URL).newBuilder();
        urlBuilder.addPathSegment(app_name);
        String url = urlBuilder.build().toString();
        okhttp3.RequestBody body = RequestBody.create(JSON, PersonagemRealm.realmToJson(personagens));
            Request request;
        if(prefer400){
            request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Prefer", "status=400")
                    .build();
        }else{
            request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
        }

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                sp.edit().putBoolean("PendingSync",true).commit();
                backgroundThreadToast(context,"Falha em sincronizar favorito: \n"+e.getMessage()+"\n"+e.getCause().toString());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    if(response.code()==400) {
                        JsonElement json = new JsonParser().parse(response.body().string());
                        backgroundThreadToast(context, json.getAsJsonObject().get("error").getAsString() + "\n" + json.getAsJsonObject().get("error_message").getAsString());
                    }
                    throw new IOException("Unexpected code " + response);

                } else {

                    sp.edit().putBoolean("PendingSync",false).commit();
                    JsonElement json = new JsonParser().parse(response.body().string());
                     backgroundThreadToast(context, json.getAsJsonObject().get("status").getAsString()+"\n"+json.getAsJsonObject().get("message").getAsString());
                }
            }
        });
    }
    public static void backgroundThreadToast(final Context context,
                                                  final String msg) {
        if (context != null && msg != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
