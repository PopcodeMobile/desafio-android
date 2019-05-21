package kleyton.starwarswiki;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    String url_base_favorites = "http://private-782d3-starwarsfavorites.apiary-mock.com/favorite/{id}";
    TextView nameTv, heightTv, genderTv, massTv, hairTv, skinTv, eyeTv, birthTv, homeworldTv, speciesTv;
    SharedPreferences prefsQueue;
    LinearLayout layout;
    ImageView favIcon;
    SQLiteDatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.detail_activity_name);
        db = new SQLiteDatabaseHandler(this);
        prefsQueue = this.getSharedPreferences("RequestQueue", Context.MODE_PRIVATE);

        Intent intent = getIntent();
        final String name   = intent.getStringExtra("name");
        String height       = intent.getStringExtra("height");
        String gender       = intent.getStringExtra("gender");
        String mass         = intent.getStringExtra("mass");
        String hair_color   = intent.getStringExtra("hair_color");
        String skin_color   = intent.getStringExtra("skin_color");
        String eye_color    = intent.getStringExtra("eye_color");
        String birth_year   = intent.getStringExtra("birth_year");
        String homeworldUrl = intent.getStringExtra("homeworld");
        String speciesUrl   = intent.getStringExtra("species");
        String isbookmark   = intent.getStringExtra("isbookmark");

        layout      = findViewById(R.id.layout_detail);
        nameTv      = findViewById(R.id.name_detail_textview);
        heightTv    = findViewById(R.id.height_detail_textview);
        genderTv    = findViewById(R.id.gender_detail_textview);
        massTv      = findViewById(R.id.mass_detail_textview);
        hairTv      = findViewById(R.id.hair_color_detail_textview);
        skinTv      = findViewById(R.id.skin_color_detail_textview);
        eyeTv       = findViewById(R.id.eye_color_detail_textview);
        birthTv     = findViewById(R.id.birth_year_detail_textview);
        homeworldTv = findViewById(R.id.homeworld_detail_textview);
        speciesTv   = findViewById(R.id.species_detail_textview);
        favIcon     = findViewById(R.id.fav_icon_detail);

        nameTv.setText(name);
        heightTv.setText(height);
        genderTv.setText(gender);
        massTv.setText(mass);
        hairTv.setText(hair_color);
        skinTv.setText(skin_color);
        eyeTv.setText(eye_color);
        birthTv.setText(birth_year);
        homeworldTv.setText("Carregando...");
        speciesTv.setText("Carregando...");

        if (db.homeWorldUpdated(name)) {
            homeworldTv.setText(db.getHomeworld(name));
            Log.d("DETAIL", "HOMEWORLD ALREADY UPDATED");
        } else {
            if (isConnected(getApplicationContext())) updateHomeworld(homeworldUrl, name);
        }

        if (db.speciesUpdated(name)) {
            speciesTv.setText(db.getSpecies(name));
            Log.d("DETAIL", "SPECIES ALREADY UPDATED");
        } else {
            if (isConnected(getApplicationContext())) updateSpecies(speciesUrl, name);
        }

        if (db.isBookmark(name)) favIcon.setAlpha(1.0f);
        favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (db.isBookmark(name)) {
                    db.removeBookmark(name);
                    favIcon.setAlpha(0.2f);
                    Snackbar.make(layout, "Favorito removido", Snackbar.LENGTH_LONG).show();
                } else {
                    db.setBookmark(name);
                    if (isConnected(getApplicationContext())){
                        sendBookmarkRequest(name);
                    } else {
                        saveToRequestQueue(name);
                    }
                    favIcon.setAlpha(1.0f);
                    Snackbar.make(layout, "Favorito adicionado", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    public void updateHomeworld(String url, String name) {
        final String personName = name;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley", response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String homeworld = jsonObject.getString("name");

                    if (homeworld.contains("'")) {
                        int index = homeworld.indexOf("'");
                        String newString = homeworld.substring(0, index) + "'" + homeworld.substring(index);
                        db.updateHomeworld(newString, personName);
                    } else {
                        db.updateHomeworld(homeworld, personName);
                    }

                    Log.d("DETAIL", "HOMEWORLD UPDATED");
                    homeworldTv.setText(homeworld);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                if (error.toString().equals("com.android.volley.TimeoutError")) {
                    homeworldTv.setText("timeout error");
                } else {
                    homeworldTv.setText("error");
                }
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    public void updateSpecies(String url, String name) {
        final String personName = name;
        if (!url.isEmpty()) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Volley", response.toString());
                    try {
                        JSONObject jsonObject = new JSONObject(response.toString());
                        String species = jsonObject.getString("name");

                        Log.d("SQLPREVENT", species);
                        if (species.contains("'")) {
                            int index = species.indexOf("'");
                            String newString = species.substring(0, index) + "'" + species.substring(index);
                            db.updateSpecies(newString, personName);
                            Log.d("SQLPREVENT", newString);
                        } else {
                            db.updateSpecies(species, personName);
                        }

                        Log.d("DETAIL", "SPECIES UPDATED");
                        speciesTv.setText(species);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley", error.toString());
                    if (error.toString().equals("com.android.volley.TimeoutError")) {
                        speciesTv.setText("timeout error");
                    } else {
                        speciesTv.setText("error");
                    }
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);
        } else {
            speciesTv.setText("unknown");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                int requestCode = 1;
                startActivityForResult(intent, requestCode);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    private void parseVolleyError(VolleyError error) {
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            JSONObject data = new JSONObject(responseBody);
            String err = data.getString("error");
            String error_message = data.getString("error_message");
            Toast.makeText(getApplicationContext(), err + "\n" + error_message, Toast.LENGTH_LONG).show();
            Log.e("ERROR_RESPONSE", data.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void sendBookmarkRequest(final String name) {
        final SharedPreferences pref = this.getSharedPreferences("StarWarsPref", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_base_favorites, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status         = jsonObject.getString("status");
                    String message        = jsonObject.getString("message");
                    Toast.makeText(getApplicationContext(), status + "\n" + message, Toast.LENGTH_LONG).show();
                    if (status.equals("success")) Snackbar.make(layout, name + " favoritado", Snackbar.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                parseVolleyError(error);
                saveToRequestQueue(name);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                String parity = pref.getString("parity", "even");
                if (parity.equals("odd")) {
                    headers.put("Prefer", "status=400");
                    editor.putString("parity", "even");
                } else {
                    editor.putString("parity", "odd");
                }
                editor.apply();
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", name);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void saveToRequestQueue(String name) {
        SharedPreferences prefs = this.getSharedPreferences("RequestQueue", Context.MODE_PRIVATE);
        String json = prefs.getString("queue", "");
        Gson gson = new Gson();
        if (json.isEmpty()) {
            Log.e("SAVE PREF", "JSON EMPTY");
            List<String> queue = new ArrayList<>();
            queue.add(name);
            String newJson = gson.toJson(queue);
            Log.d("NEWJSON", newJson);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("queue", newJson);
            editor.apply();
        } else {
            Log.d("JSON", json);
            Type type = new TypeToken<List<String>>(){}.getType();
            List<String> queue = gson.fromJson(json, type);
            if(!nameSavedInQueue(queue, name)) {
                queue.add(name);
                String newJson = gson.toJson(queue);
                Log.d("NEWJSON", newJson);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("queue", newJson);
                editor.apply();
            }
        }

    }

    public boolean nameSavedInQueue(List<String> queue, String name) {
        for (int i = 0; i < queue.size(); i++) {
            if (queue.get(i).equals(name)) return true;
        }
        return false;
    }

    private boolean isConnected(Context context) {
        NetworkInfo netInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting() && netInfo.isAvailable();
    }

}
