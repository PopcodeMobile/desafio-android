package kleyton.starwarswiki;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayout searchLinearLayout;
    EditText searchEditText;
    ImageView submitSearchImage;
    FloatingActionButton floatingActionButton;
    LinearLayoutManager linearLayoutManager;
    List<Person> personList;
    RecyclerView.Adapter adapter;
    String url = "https://swapi.co/api/people/?format=json";
    String url_search = "https://swapi.co/api/people/?search=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchLinearLayout = findViewById(R.id.search);
        searchEditText = findViewById(R.id.searchEditText);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        submitSearchImage = findViewById(R.id.submitSearchImage);

        recyclerView = findViewById(R.id.main_list_recyclerview);
        personList = new ArrayList<>();
        adapter = new PersonAdapter(getApplicationContext(), personList);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        getData();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchLinearLayout.setVisibility(View.VISIBLE);
                searchEditText.requestFocus();
            }
        });

        submitSearchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personList.clear();
                String query = searchEditText.getText().toString().trim();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url_search + query, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Volley", response.toString());
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String name = jsonObject.getString("name");
                                String height = jsonObject.getString("height");
                                String gender = jsonObject.getString("gender");
                                String mass = jsonObject.getString("mass");
                                String hair_color = jsonObject.getString("hair_color");
                                String skin_color = jsonObject.getString("skin_color");
                                String eye_color = jsonObject.getString("eye_color");
                                String birth_year = jsonObject.getString("birth_year");
                                String homeworld = jsonObject.getString("homeworld");
                                String species = jsonObject.getString("species");

                                Person person = new Person(name, height, gender, mass, hair_color, skin_color, eye_color, birth_year, homeworld, species);
                                personList.add(person);
                                adapter.notifyDataSetChanged();
                            }
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

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(jsonObjectRequest);
            }
        });

    }

    private void getData() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley", response.toString());
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String height = jsonObject.getString("height");
                        String gender = jsonObject.getString("gender");
                        String mass = jsonObject.getString("mass");
                        String hair_color = jsonObject.getString("hair_color");
                        String skin_color = jsonObject.getString("skin_color");
                        String eye_color = jsonObject.getString("eye_color");
                        String birth_year = jsonObject.getString("birth_year");
                        String homeworld = jsonObject.getString("homeworld");
                        String species = jsonObject.getString("species");

                        Person person = new Person(name, height, gender, mass, hair_color, skin_color, eye_color, birth_year, homeworld, species);
                        personList.add(person);
                        adapter.notifyDataSetChanged();
                    }
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
}
