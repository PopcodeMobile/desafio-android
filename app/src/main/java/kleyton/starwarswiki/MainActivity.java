package kleyton.starwarswiki;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayout backFabWrapper;
    CardView searchWrapper;
    EditText searchEditText;
    ImageView submitSearchImage;
    FloatingActionButton searchFab;
    LinearLayoutManager linearLayoutManager;
    RecyclerView.Adapter adapter;
    String url_page = "https://swapi.co/api/people/?page=";
    String url_search = "https://swapi.co/api/people/?search=";
    String url_base_favorites = "http://private-782d3-starwarsfavorites.apiary-mock.com/favorite/{id}";
    boolean searchFabIsSend = false;
    SQLiteDatabaseHandler db;
    List<Person> people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchWrapper     = findViewById(R.id.search);
        searchEditText    = findViewById(R.id.searchEditText);
        searchFab         = findViewById(R.id.floatingActionButton);
        backFabWrapper    = findViewById(R.id.fabWrapper);
        submitSearchImage = findViewById(R.id.submitSearchImage);
        recyclerView      = findViewById(R.id.main_list_recyclerview);

        db                  = new SQLiteDatabaseHandler(this);
        people              = new ArrayList<>();
        adapter             = new PersonAdapter(getApplicationContext(), people);
        linearLayoutManager = new LinearLayoutManager(this);

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        if (isConnected(this)) {
            requestPeopleFromDatabase();
            requestPeopleFromNetwork();
            sendBookmarkRequest("Dooku");
        } else {
            requestPeopleFromDatabase();
        }


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
                requestPeopleFromDatabase();
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
        people.clear();
        adapter.notifyDataSetChanged();
    }

    private void submitSearch() {
        people.clear();
        String query = searchEditText.getText().toString().trim();
        searchRequest(url_search + query);
    }

    private void requestPeopleFromNetwork() {
        for (int i = 1; i < 9; i++) {
            requestData(url_page + i);
        }
    }

    private void requestPeopleFromDatabase() {
        List<Person> people = db.getAllCharacters();
        for (int i = 0; i < people.size(); i++) {
            this.people.add(people.get(i));
            adapter.notifyDataSetChanged();
        }
    }

    private void searchRequest(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
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
                        String isbookmark = "no";

                        Person person = new Person(name, height, gender, mass, hair_color, skin_color,
                                eye_color, birth_year, homeworld, species, isbookmark);
                        people.add(person);
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

    private void requestData(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
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
                        String isbookmark = "no";

                        Person person = new Person(name, height, gender, mass, hair_color, skin_color,
                                                    eye_color, birth_year, homeworld, species, isbookmark);

                        people.add(person);
                        if (!db.personExists(person)) {
                            db.insertPerson(person);
                            adapter.notifyDataSetChanged();
                            Log.d("MAIN", "PERSON INSERTED");
                        } else {
                            //Log.d("MAIN", "PERSON EXISTS");
                        }

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

    public void parseVolleyError(VolleyError error) {
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

    public void sendBookmarkRequest(final String name) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_base_favorites, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("VOLLEYRESP", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                parseVolleyError(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Prefer", "status=400");
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

    public boolean isConnected(Context context) {
        NetworkInfo netInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting() && netInfo.isAvailable();
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
