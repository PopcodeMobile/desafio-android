package com.example.starwarswiki;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starwarswiki.adapters.PersonListAdapter;
import com.example.starwarswiki.handlers.*;
import com.example.starwarswiki.structural.*;
import com.example.starwarswiki.view_models.MainViewModel;
import com.google.gson.Gson;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        PeopleHandler.MyCallbackInterface, PlanetsNameHandler.MyCallbackInterface,
        SpeciesNameHandler.MyCallbackInterface, SearchView.OnQueryTextListener,
        PersonListAdapter.OnPersonClickListener, PersonListAdapter.OnCheckedFavListener,
        FavHandler.FavCallBack {
    private MainViewModel mViewModel;
    private PersonListAdapter adapter;
    private SearchView searchView;
    private List<FavLogItem> failedFavLogList;
    private Boolean ranOnce = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new PersonListAdapter
                (this,this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        Boolean ranOnce = getSharedPreferences
//                ("PREFERENCE", MODE_PRIVATE).getBoolean("ran", true);

//        Check if network is available before starting to fetch

        if(isInternetAvailable()) {
            new PeopleHandler(this).execute("https://swapi.co/api/people?format=json");
            new PlanetsNameHandler(this).execute("https://swapi.co/api/planets/?format=json");
            new SpeciesNameHandler(this).execute("https://swapi.co/api/species/?format=json");
            mViewModel.getAllPerson();

            //Get List Of failed favourites
            //if(ranOnce) {
                Toast.makeText(this,"AAAAAAAA", Toast.LENGTH_LONG);
                mViewModel.checkFailedFavLogs().observe(this, new Observer<List<FavLogItem>>() {
                    @Override
                    public void onChanged(List<FavLogItem> favLogItems) {
                        if(favLogItems != null) {
                            mViewModel.removeFavLogs(); //Clear the list
                            for(int i = 0; i < favLogItems.size(); i++){
                                String name = favLogItems.get(i).getName();
                                new FavHandler(MainActivity.this).execute(name);
                                mViewModel.setAsFavorite(name, 1);  //Tries to set as Fav
                            }
                            mViewModel.checkFailedFavLogs().removeObserver(this);
                        }
                    }
                });
                //ranOnce = false;
            //}
        } else {
            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
        }

        mViewModel.getAllPerson();
        mViewModel.getAllPerson().observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(List<Person> listOfPerson) {
                adapter.setListOfPerson(listOfPerson);
            }
        });
    }

    //Search functions

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mViewModel.getAllPerson().observe(MainActivity.this, new Observer<List<Person>>() {
                    @Override
                    public void onChanged(List<Person> personList) {
                        adapter.setListOfPerson(personList);
                    }
                });
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        queryString(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        queryString(query);
        return true;
    }

    public void queryString (String query) {
        mViewModel.queryByName("%"+query+"%").observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(List<Person> listOfPerson) {
                adapter.setListOfPerson(listOfPerson);
            }
        });
    }

    @Override
    public void onPersonClick(int position) {
        Intent intent = new Intent(this, DetailsActivity.class);
        String person = new Gson().toJson(adapter.getPerson(position));
        intent.putExtra("person", person);
        startActivity(intent);
    }

    @Override
    public void onFavClick(String name, int fav) {
        if(fav != 0){
            if(isInternetAvailable()) {
                //Only add with internet connection
                new FavHandler(this).execute(name);
                mViewModel.setAsFavorite(name, fav);
            } else {
                //There's no internet so fav will be set back to 0, (not added)
                Toast.makeText(getApplicationContext(), "No internet connection\n"+name+": will not be processed", Toast.LENGTH_LONG).show();
                mViewModel.setAsFavorite(name,0);
            }
        } else {
            //In this case fav == 0, the app will always remove
            mViewModel.setAsFavorite(name, fav);
        }

    }

//  >>>>>>>> Async data fetch >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    /**
     * People list request completed
     * @param result People object
     */
    @Override
    public void onRequestCompleted(People result) {
        if(result != null) {
            mViewModel.insert(result.getList());
        }
        if(result.getNext() != null) {
            new PeopleHandler(this).execute(result.getNext());
        }
    }

    /**
     * Planets list request completed
     * @param result Planets object
     */
    @Override
    public void onRequestCompleted(Planets result) {
        List<Planet> planets = result.getListOfPlanet();
        URI uri;
        String[] segments;
        String idStr;
        if(result != null) {
            //Saving url as ids
            for (int i = 0; i < result.getListOfPlanet().size(); i++) {
                try {
                    uri = new URI(planets.get(i).getUrl());
                    segments = uri.getPath().split("/");
                    idStr = segments[segments.length-1];
                    planets.get(i).setUrl(idStr);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }


            }
            mViewModel.insertPlanets(planets);
        }
        if(result.getNext() != null) {
            new PlanetsNameHandler(this).execute(result.getNext());
        }
    }

    /**
     * Species list request completed
     * @param result Species object
     */
    @Override
    public void onRequestCompleted(Species result) {
        List<Specie> species = result.getListOfSpecie();
        URI uri;
        String[] segments;
        String idStr;
        if(result != null) {
            //Saving url as ids
            for (int i = 0; i < result.getListOfSpecie().size(); i++) {
                try {
                    uri = new URI(species.get(i).getUrl());
                    segments = uri.getPath().split("/");
                    idStr = segments[segments.length-1];
                    species.get(i).setUrl(idStr);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }


            }
            mViewModel.insertSpecies(species);
        }
        if(result.getNext() != null) {
            new SpeciesNameHandler(this).execute(result.getNext());
        }
    }

    /**
     * Favorite add request completed
     * @param result FavLogItem Object
     */
    @Override
    public void onRequestCompleted(FavLogItem result) {
        if(result != null) {
            if(result.getStatus() != null) { //200
                Toast.makeText(this,
                        "[" + result.getName()+"] Added\n" + result.getMessage(), Toast.LENGTH_LONG).show();
            } else { //400
                Toast.makeText(this,
                        "[" + result.getName()+"] Failed\n" + result.getErrorMessage(), Toast.LENGTH_LONG).show();
                mViewModel.favFailed(result);
            }
        }
    }

    public boolean isInternetAvailable () {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if (connectivityManager != null) {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }
            return networkInfo != null && networkInfo.isConnected();
        } catch (NullPointerException e) {
            return false;
        }

    }
}
