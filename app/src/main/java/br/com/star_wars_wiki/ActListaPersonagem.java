package br.com.star_wars_wiki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import br.com.star_wars_wiki.adapter.PeopleAdapter;
import br.com.star_wars_wiki.entity.People;
import br.com.star_wars_wiki.entity.SWModelList;
import br.com.star_wars_wiki.network.StarWarsApi;
import br.com.star_wars_wiki.utils.RecyclerItemClickListener;
import br.com.star_wars_wiki.view_model.PeopleViewModel;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ActListaPersonagem extends AppCompatActivity {

    private MaterialSearchView searchView;
    private ProgressBar progressBar;
    private RecyclerView recyclerPeopleList;
    private PeopleViewModel peopleViewModel;
    private int page = 0;
    private boolean next = true;
    private int nextPage = 1;
    private List<People> peopleList = new ArrayList<>();
    private PeopleAdapter peopleAdapter = new PeopleAdapter(peopleList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_lista_personagens);

        Toolbar toolbar = findViewById(R.id.toolbar_people);
        toolbar.setTitle("Personagens");
        setSupportActionBar(toolbar);

        StarWarsApi.init();

        progressBar = findViewById(R.id.progress_bar);
        recyclerPeopleList = findViewById(R.id.lista_people);

        //Inicialização do ViewModel
        peopleViewModel = new ViewModelProvider(this).get(PeopleViewModel.class);
        peopleViewModel.getAllPeople().observe(this, new Observer<List<People>>() {
            @Override
            public void onChanged(List<People> peopleListRecebido) {
                peopleList = peopleListRecebido;
                peopleAdapter.setPeopleList(peopleList);
                recyclerPeopleList.setAdapter(peopleAdapter);
                if(peopleList.size() > 0){
                    nextPage = peopleList.get(peopleList.size() - 1).getNextPage();
                }
                if(peopleList.size() == 0){
                    getAllPeople();
                }
            }
        });

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

        //Listener do RecyclerView para detectar o toque em um item
        recyclerPeopleList.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerPeopleList,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                               Intent it = new Intent(ActListaPersonagem.this, ActPersonagem.class);
                               it.putExtra("people", peopleList.get(position));
                               startActivity(it);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {
                            }
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            }
                        }
                )
        );


        searchView = findViewById(R.id.svApontamento);
        //Listener para o search view
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                peopleAdapter.setPeopleList(peopleList);
                recyclerPeopleList.setAdapter(peopleAdapter);
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if( newText != null && !newText.isEmpty() ){
                    searchPeople(newText.toLowerCase());
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        //Configura botão de pesquisa
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return super.onCreateOptionsMenu(menu);
    }

    public void getAllPeople(){
        if(next && (progressBar.getVisibility() == View.GONE)){
            progressBar.setVisibility(View.VISIBLE);
            StarWarsApi.getApi().getAllPeople(nextPage, new Callback<SWModelList<People>>() {
                @Override
                public void success(SWModelList<People> peopleSWModelList, Response response) {
                    peopleList = new ArrayList<>();
                    peopleList.addAll(peopleSWModelList.results);
                    for(int i = 0; i < peopleList.size(); i++){//Fiz um mecanismo para capturar a página dos resultados, isso se fez necessário, pois quando a busca não é realizada completamente atrvés do scroll listener, existe um problema quando o usuário volta a tela inicial e começa a requisitar novos resultados
                        if(peopleSWModelList.next != null){
                            String aux = peopleSWModelList.next;
                            peopleList.get(i).setNextPage(Integer.parseInt(String.valueOf(peopleSWModelList.next.charAt(aux.length() - 1))));
                        }
                    }
                    peopleViewModel.insert(peopleList);
                    next = peopleSWModelList.hasMore();

                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }

    }

    public void searchPeople(String texto){
        List<People> listaPeoplePesquisados = new ArrayList<>();

        for ( int i=0; i< peopleList.size(); i++){

            People people = peopleList.get(i);

            String nome = people.getName().toLowerCase();

            if( nome.contains( texto )){
                listaPeoplePesquisados.add(people);
            }
        }

        peopleAdapter = new PeopleAdapter(listaPeoplePesquisados);
        recyclerPeopleList.setAdapter(peopleAdapter);
        peopleAdapter.notifyDataSetChanged();
    }
}