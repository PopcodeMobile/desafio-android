package kleyton.starwarswiki;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String url_people = "https://swapi.co/api/people/?page=";
    String url_search = "https://swapi.co/api/people/?search=";
    String url_base_favorites = "http://private-782d3-starwarsfavorites.apiary-mock.com/favorite/{id}";
    RecyclerView recyclerView;
    LinearLayout backFabWrapper;
    CardView searchWrapper;
    EditText searchEditText;
    ImageView submitSearchImage;
    FloatingActionButton searchFab;
    LinearLayoutManager linearLayoutManager;
    RecyclerView.Adapter adapter;
    EndlessRecyclerViewScrollListener scrollListener;
    SharedPreferences prefsQueue;
    SharedPreferences prefs;
    RequestQueue requestQueue;
    boolean searchFabIsSend = false;
    boolean isFiltered = false;
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

        requestQueue        = Volley.newRequestQueue(this);
        db                  = new SQLiteDatabaseHandler(this);
        people              = new ArrayList<>();
        adapter             = new PersonAdapter(getApplicationContext(), people);
        linearLayoutManager = new LinearLayoutManager(this);
        prefsQueue          = this.getSharedPreferences("RequestQueue", Context.MODE_PRIVATE);
        prefs               = this.getSharedPreferences("StarWarsPref", Context.MODE_PRIVATE);

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        if (!requestPeopleFromDatabase()) {
            if (isConnected(this)) {
                requestData(url_people + 1);
                adapter.notifyDataSetChanged();
                scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                        int pageStartingByTwo = page + 1;
                        requestData(url_people + pageStartingByTwo);
                    }
                };
                recyclerView.addOnScrollListener(scrollListener);

            } else {
                Snackbar.make(recyclerView, "Sem conexão", Snackbar.LENGTH_LONG).show();
            }
        } else {
            if (isConnected(this)) {
                resendFailedBookmarkRequests();
                updateDatabase();
            } else {
                Snackbar.make(recyclerView, "Sem conexão", Snackbar.LENGTH_LONG).show();
            }
        }

        searchFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backFabWrapper.setVisibility(View.VISIBLE);
                people.clear();
                if (!searchFabIsSend) {
                    searchWrapper.setVisibility(View.VISIBLE);
                    searchEditText.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT);
                    searchFabIsSend = true;
                } else {
                    if (isConnected(getApplicationContext())) searchRequest(searchEditText.getText().toString().trim());
                    else {
                        searchDatabase(searchEditText.getText().toString().trim());
                        Toast.makeText(getApplicationContext(), "Pesquisa offline", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        backFabWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backFabWrapper.setVisibility(View.GONE);
                searchWrapper.setVisibility(View.GONE);
                searchEditText.getText().clear();
                clearRecyclerView();
                requestPeopleFromDatabase();
                searchFabIsSend = false;
                hideKeyboard();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        people.clear();
        requestPeopleFromDatabase();
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter) {
            if (!isFiltered) {
                clearRecyclerView();
                requestBookmarksFromDatabase();
                isFiltered = true;
                item.setIcon(R.drawable.filter_on);
                Snackbar.make(recyclerView, "Mostrando somente favoritos", Snackbar.LENGTH_LONG).show();
            } else {
                clearRecyclerView();
                requestPeopleFromDatabase();
                isFiltered = false;
                item.setIcon(R.drawable.filter_off);
                Snackbar.make(recyclerView, "Mostrando todos", Snackbar.LENGTH_LONG).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void clearRecyclerView() {
        people.clear();
        adapter.notifyDataSetChanged();
    }

    private boolean requestPeopleFromDatabase() {
        List<Person> people = db.getPeople();
        for (int i = 0; i < people.size(); i++) {
            this.people.add(people.get(i));
        }
        if (this.people.size() > 1) {
            adapter.notifyDataSetChanged();
            return true;
        } else return false;
    }

    private void updateDatabase() {
        for (int i = 1; i < 10; i++) {
            requestDataToUpdateDatabase(url_people + i);
        }
    }

    private void searchDatabase(String name) {
        List<Person> people = db.searchPerson(name);
        for (int i = 0; i < people.size(); i++) {
            this.people.add(people.get(i));
            adapter.notifyDataSetChanged();
        }
    }

    private void requestBookmarksFromDatabase() {
        List<Person> people = db.getAllBookmarks();
        for (int i = 0; i < people.size(); i++) {
            this.people.add(people.get(i));
            adapter.notifyDataSetChanged();
        }
    }

    private void requestData(String url) {
        final Snackbar snackbar = Snackbar.make(recyclerView, "Carregando itens...", Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name           = jsonObject.getString("name");
                        String height         = jsonObject.getString("height");
                        String gender         = jsonObject.getString("gender");
                        String mass           = jsonObject.getString("mass");
                        String hair_color     = jsonObject.getString("hair_color");
                        String skin_color     = jsonObject.getString("skin_color");
                        String eye_color      = jsonObject.getString("eye_color");
                        String birth_year     = jsonObject.getString("birth_year");
                        String homeworld      = jsonObject.getString("homeworld");
                        String isbookmark     = "no";
                        String species        = "";
                        try {
                            species = (String) jsonObject.getJSONArray("species").get(0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Person person = new Person(name, height, gender, mass, hair_color, skin_color,
                                                    eye_color, birth_year, homeworld, species, isbookmark);

                        if (!db.personExists(name)){
                            db.insertPerson(person);
                            people.add(person);
                            adapter.notifyDataSetChanged();
                        }
                        snackbar.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                snackbar.dismiss();
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    private void requestDataToUpdateDatabase(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name           = jsonObject.getString("name");
                        String height         = jsonObject.getString("height");
                        String gender         = jsonObject.getString("gender");
                        String mass           = jsonObject.getString("mass");
                        String hair_color     = jsonObject.getString("hair_color");
                        String skin_color     = jsonObject.getString("skin_color");
                        String eye_color      = jsonObject.getString("eye_color");
                        String birth_year     = jsonObject.getString("birth_year");
                        String homeworld      = jsonObject.getString("homeworld");
                        String isbookmark     = "no";
                        String species        = "";
                        try {
                            species = (String) jsonObject.getJSONArray("species").get(0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Person person = new Person(name, height, gender, mass, hair_color, skin_color,
                                eye_color, birth_year, homeworld, species, isbookmark);

                        if (db.personExists(name)) {
                            String newIsbookmark     = "no";
                            if (db.isBookmark(name)) newIsbookmark = "yes";
                            Person personUpdated = new Person(name, height, gender, mass, hair_color, skin_color,
                                    eye_color, birth_year, db.getHomeworld(name), db.getSpecies(name), newIsbookmark);
                            db.updatePerson(personUpdated);
                        } else {
                            db.insertPerson(person);
                            people.add(person);
                            adapter.notifyDataSetChanged();
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

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    private void searchRequest(String name) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Pesquisando...");
        progressDialog.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url_search + name, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name           = jsonObject.getString("name");
                        String height         = jsonObject.getString("height");
                        String gender         = jsonObject.getString("gender");
                        String mass           = jsonObject.getString("mass");
                        String hair_color     = jsonObject.getString("hair_color");
                        String skin_color     = jsonObject.getString("skin_color");
                        String eye_color      = jsonObject.getString("eye_color");
                        String birth_year     = jsonObject.getString("birth_year");
                        String homeworld      = jsonObject.getString("homeworld");
                        String isbookmark     = "no";
                        String species        = "";
                        try {
                            species = (String) jsonObject.getJSONArray("species").get(0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Person person = new Person(name, height, gender, mass, hair_color, skin_color,
                                eye_color, birth_year, homeworld, species, isbookmark);
                        people.add(person);
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    public void resendFailedBookmarkRequests() {
        SharedPreferences prefs = this.getSharedPreferences("RequestQueue", Context.MODE_PRIVATE);
        String json = prefs.getString("queue", "");
        Gson gson = new Gson();
        if (json.isEmpty()) {
            Log.e("SAVE PREF", "JSON EMPTY");
        } else {
            Log.d("JSON", json);
            Type type = new TypeToken<List<String>>(){}.getType();
            List<String> queue = gson.fromJson(json, type);
            for (int i = 0; i < queue.size(); i++) {
                Log.d("Resend", queue.get(i));
                sendBookmarkRequest(queue.get(i));
            }
        }
    }

    private void parseVolleyError(VolleyError error) {
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            JSONObject data = new JSONObject(responseBody);
            String err = data.getString("error");
            String error_message = data.getString("error_message");
            Toast.makeText(getApplicationContext(), err + "\n" + error_message, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void sendBookmarkRequest(final String name) {
        final SharedPreferences prefQueue = this.getSharedPreferences("RequestQueue", Context.MODE_PRIVATE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_base_favorites, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status         = jsonObject.getString("status");
                    String message        = jsonObject.getString("message");
                    Toast.makeText(getApplicationContext(), status + "\n" + message, Toast.LENGTH_LONG).show();

                    if (status.equals("success")) {
                        Gson gson = new Gson();
                        String json = prefQueue.getString("queue", "");
                        Type type = new TypeToken<List<String>>(){}.getType();
                        List<String> queue = gson.fromJson(json, type);
                        for (int i = 0; i < queue.size(); i++) {
                            if (queue.get(i).equals(name)) queue.remove(i);
                        }
                        String newJson = gson.toJson(queue);
                        Log.d("NEWJSON", newJson);
                        SharedPreferences.Editor queueEditor = prefQueue.edit();
                        queueEditor.putString("queue", newJson);
                        queueEditor.apply();
                        Snackbar.make(recyclerView, name + " favoritado", Snackbar.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                parseVolleyError(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences.Editor editor = prefs.edit();

                Map<String, String> headers = new HashMap<String, String>();
                String parity = prefs.getString("parity", "even");
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
        requestQueue.add(stringRequest);
    }

    private boolean isConnected(Context context) {
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
