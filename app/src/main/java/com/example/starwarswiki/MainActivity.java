package com.example.starwarswiki;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starwarswiki.adapters.PersonListAdapter;
import com.example.starwarswiki.handlers.NetworkHander;
import com.example.starwarswiki.handlers.PeopleHandler;
import com.example.starwarswiki.handlers.PlanetsNameHandler;
import com.example.starwarswiki.handlers.SpeciesNameHandler;
import com.example.starwarswiki.structural.People;
import com.example.starwarswiki.structural.Person;
import com.example.starwarswiki.structural.Planet;
import com.example.starwarswiki.structural.Planets;
import com.example.starwarswiki.structural.Specie;
import com.example.starwarswiki.structural.Species;
import com.example.starwarswiki.view_models.MainViewModel;
import com.google.gson.Gson;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        PeopleHandler.MyCallbackInterface, PlanetsNameHandler.MyCallbackInterface,
        SpeciesNameHandler.MyCallbackInterface, SearchView.OnQueryTextListener,
        PersonListAdapter.OnPersonClickListener, PersonListAdapter.OnCheckedFavListener {
    private MainViewModel mViewModel;
    private PersonListAdapter adapter;
    private SearchView searchView;
    private NetworkHander networkHander;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new PersonListAdapter(this,this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        Check if network is available before starting to fetch
        if(isInternetAvailable()) {
            new PeopleHandler(this).execute("https://swapi.co/api/people?format=json");
            new PlanetsNameHandler(this).execute("https://swapi.co/api/planets/?format=json");
            new SpeciesNameHandler(this).execute("https://swapi.co/api/species/?format=json");
        } else {
           Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
            mViewModel.getAllPerson();
        }
        networkHander = new NetworkHander(mViewModel);

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
        mViewModel.setAsFavorite(name, fav);
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


//    @Override
//    protected void onResume() {
//        Toast toast = Toast.makeText(this, "Welcome Back!", Toast.LENGTH_LONG);
//        toast.show();
//        super.onResume();
//    }
}
