package kleyton.starwarswiki;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {

    TextView nameTv, heightTv, genderTv, massTv, hairTv, skinTv, eyeTv, birthTv, homeworldTv, speciesTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.detail_activity_name);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String height = intent.getStringExtra("height");
        String gender = intent.getStringExtra("gender");
        String mass = intent.getStringExtra("mass");
        String hair_color = intent.getStringExtra("hair_color");
        String skin_color = intent.getStringExtra("skin_color");
        String eye_color = intent.getStringExtra("eye_color");
        String birth_year = intent.getStringExtra("birth_year");
        String homeworldUrl = intent.getStringExtra("homeworld");
        String speciesUrl = intent.getStringExtra("species");
        String isbookmark = intent.getStringExtra("isbookmark");

        getHomeworld(homeworldUrl);
        getSpecies(speciesUrl);

        nameTv = findViewById(R.id.name_detail_textview);
        heightTv = findViewById(R.id.height_detail_textview);
        genderTv = findViewById(R.id.gender_detail_textview);
        massTv = findViewById(R.id.mass_detail_textview);
        hairTv = findViewById(R.id.hair_color_detail_textview);
        skinTv = findViewById(R.id.skin_color_detail_textview);
        eyeTv = findViewById(R.id.eye_color_detail_textview);
        birthTv = findViewById(R.id.birth_year_detail_textview);
        homeworldTv = findViewById(R.id.homeworld_detail_textview);
        speciesTv = findViewById(R.id.species_detail_textview);

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
    }

    public void getHomeworld(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley", response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String homeworld = jsonObject.getString("name");
                    homeworldTv.setText(homeworld);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    public void getSpecies(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley", response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String species = jsonObject.getString("name");
                    speciesTv.setText(species);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
