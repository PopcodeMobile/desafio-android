package kleyton.starwarswiki;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    LinearLayout searchLinearLayout, backFabWrapper;
    CardView searchWrapper;
    EditText searchEditText;
    ImageView submitSearchImage;
    FloatingActionButton searchFab;
    LinearLayoutManager linearLayoutManager;
    List<Person> personList;
    RecyclerView.Adapter adapter;
    String url_page = "https://swapi.co/api/people/?page=";
    String url_search = "https://swapi.co/api/people/?search=";
    boolean searchFabIsSend = false;
    boolean backFabClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchWrapper = findViewById(R.id.search);
        searchEditText = findViewById(R.id.searchEditText);
        searchFab = findViewById(R.id.floatingActionButton);
        backFabWrapper = findViewById(R.id.fabWrapper);
        submitSearchImage = findViewById(R.id.submitSearchImage);

        recyclerView = findViewById(R.id.main_list_recyclerview);
        personList = new ArrayList<>();
        adapter = new PersonAdapter(getApplicationContext(), personList);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        requestAllCharacters();

        searchFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backFabWrapper.setVisibility(View.VISIBLE);
                if (!searchFabIsSend) {
                    searchWrapper.setVisibility(View.VISIBLE);
                    searchEditText.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT);
                    searchFabIsSend = true;
                } else {
                    submitSearch();
                }
            }
        });

        backFabWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backFabWrapper.setVisibility(View.GONE);
                searchWrapper.setVisibility(View.GONE);
                searchEditText.getText().clear();
                clearCharacters();
                requestAllCharacters();
                searchFabIsSend = false;
                hideKeyboard();
            }
        });

        submitSearchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitSearch();
            }
        });

    }

    private void clearCharacters() {
        personList.clear();
        adapter.notifyDataSetChanged();
    }

    private void submitSearch() {
        personList.clear();
        String query = searchEditText.getText().toString().trim();
        requestData(url_search + query);
    }

    private void requestAllCharacters() {
        for (int i = 1; i < 9; i++) {
            requestData(url_page + i);
        }
    }

    private void requestData(String url) {
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
                        String species = (String) jsonObject.getJSONArray("species").get(0);

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

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
