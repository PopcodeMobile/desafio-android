package br.com.star_wars_wiki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.star_wars_wiki.adapter.PeopleAdapter;
import br.com.star_wars_wiki.entity.People;
import br.com.star_wars_wiki.entity.SWModelList;
import br.com.star_wars_wiki.network.StarWarsApi;
import br.com.star_wars_wiki.view_model.PeopleViewModel;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ActPersonagem extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView recyclerPeopleList;
    private PeopleViewModel peopleViewModel;
    private int page = 0;
    private boolean next = true;
    private ArrayList<People> peopleList = new ArrayList<>();
    private PeopleAdapter peopleAdapter = new PeopleAdapter(peopleList);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_personagens);

        StarWarsApi.init();

        progressBar = findViewById(R.id.progress_bar);
        recyclerPeopleList = findViewById(R.id.lista_people);

        //Inicialização do ViewModel
        peopleViewModel = new ViewModelProvider(this).get(PeopleViewModel.class);

        //Configuração do RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerPeopleList.setLayoutManager(layoutManager);

        //Listener do RecyclerView para detectar o fim da rolagem
        recyclerPeopleList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!recyclerPeopleList.canScrollVertically(1)){
                    getAllPeople();
                }
            }
        });

        getAllPeople();
    }

    public void getAllPeople(){
        if(next && (progressBar.getVisibility() == View.GONE)){
            progressBar.setVisibility(View.VISIBLE);
            page++;
            StarWarsApi.getApi().getAllPeople(page, new Callback<SWModelList<People>>() {
                @Override
                public void success(SWModelList<People> peopleSWModelList, Response response) {
                    next = peopleSWModelList.hasMore();
                    peopleList.addAll(peopleSWModelList.results);
                    peopleAdapter.setPeopleList(peopleList);
                    recyclerPeopleList.setAdapter(peopleAdapter);
                    peopleViewModel.insert(peopleList);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }

    }
}