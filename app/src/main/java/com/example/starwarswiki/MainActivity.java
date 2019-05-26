package com.example.starwarswiki;

import android.content.Intent;
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

import com.example.starwarswiki.handlers.PeopleHandler;
import com.example.starwarswiki.structural.People;
import com.example.starwarswiki.structural.Person;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PeopleHandler.MyCallbackInterface,
        SearchView.OnQueryTextListener, PersonListAdapter.OnPersonClickListener {
    private PersonViewModel mViewModel;
    private PersonListAdapter adapter;
    private SearchView searchView;
    private Switch favoriteSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new PersonListAdapter(this,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new PeopleHandler(this).execute("https://swapi.co/api/people?format=json");

        mViewModel.getAllPerson().observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(List<Person> listOfPerson) {
                adapter.setListOfPerson(listOfPerson);
            }
        });

//        FloatingActionButton fab =findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, status, Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                Intent intent = new Intent(MainActivity.this, NewPersonActivity.class);
//                startActivityForResult(intent, NEW_PERSON_ACTIVITY_REQUEST_CODE);
//            }
//        });
//        ActionBar actionBar = getSupportActionBar();
//        TextView textView = new TextView(getApplicationContext());
//        Typeface typeface = ResourcesCompat.getFont(this, R.font.sfdistantgalaxyalternateitalic);
//        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        textView.setLayoutParams(lp);
//        textView.setText("Star Wars Wiki");
//        textView.setTextSize(20);
//        textView.setTypeface(typeface);
//        textView.setTextColor(Color.WHITE);
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        actionBar.setCustomView(textView);
    }

    @Override
    public void onRequestCompleted(People result) {
        if(result != null) {
            mViewModel.insert(result.getList());
        }
        if(result.getNext() != null) {
            new PeopleHandler(this).execute(result.getNext());
        }
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
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        Toast toast = Toast.makeText(this, "Welcome Back!", 5);
        toast.show();
        super.onResume();
    }
}
