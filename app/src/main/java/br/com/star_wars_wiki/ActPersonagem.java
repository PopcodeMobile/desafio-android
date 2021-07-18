package br.com.star_wars_wiki;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;

import br.com.star_wars_wiki.adapter.PeopleAdapter;
import br.com.star_wars_wiki.entity.People;
import br.com.star_wars_wiki.entity.SWModelList;
import br.com.star_wars_wiki.network.StarWarsApi;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ActPersonagem extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView recyclerPeopleList;
    private int page = 1;
    private ArrayList<People> peopleList = new ArrayList<>();
    private PeopleAdapter peopleAdapter = new PeopleAdapter(peopleList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_personagens);

        StarWarsApi.init();

        progressBar = findViewById(R.id.progress_bar);
        recyclerPeopleList = findViewById(R.id.lista_people);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerPeopleList.setLayoutManager(layoutManager);
        //recyclerPeopleList.setHasFixedSize(true);
        //recyclerPeopleList.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));

        progressBar.setVisibility(View.VISIBLE);
        getAllPeople();
    }

    public void getAllPeople(){
        StarWarsApi.getApi().getAllPeople(page, new Callback<SWModelList<People>>() {
            @Override
            public void success(SWModelList<People> peopleSWModelList, Response response) {
                peopleList.addAll(peopleSWModelList.results);
                peopleAdapter.setPeopleList(peopleList);
                recyclerPeopleList.setAdapter(peopleAdapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}