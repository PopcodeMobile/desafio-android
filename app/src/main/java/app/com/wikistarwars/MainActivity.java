package app.com.wikistarwars;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import app.com.wikistarwars.Adapter.PaginationAdapter;
import app.com.wikistarwars.Api.RetrofitConfig;
import app.com.wikistarwars.Api.Service;
import app.com.wikistarwars.Model.Personagem;
import app.com.wikistarwars.Model.PersonagemResponse;
import app.com.wikistarwars.Util.PaginationScrollListener;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private PaginationAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 1;
    private int currentPage = PAGE_START;

    private SearchView searchView;

    private Service api;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        realm = Realm.getDefaultInstance();

        recyclerView = (RecyclerView) findViewById(R.id.rvPersonagens);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        adapter = new PaginationAdapter(this);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        //Creating an object of our api interface
        api = RetrofitConfig.getApiService();

        loadFirstPage();

    }

    protected boolean isConnected(){
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(isConnected())
            Toast.makeText(this,"ONLINE",Toast.LENGTH_SHORT);
        else
            Toast.makeText(this,"OFFLINE",Toast.LENGTH_SHORT);
//        realm =  Realm.getDefaultInstance();
//        RealmResults<Personagem> personagens = realm.where(Personagem.class).findAll();
//        adapter.setPersons(personagens);
//        realm.close();
    }

    private List<Personagem> fetchResults(Response<PersonagemResponse> response) {
        PersonagemResponse persons = response.body();
        return persons.getResults();
    }

    private void loadSearch(final String nome) {
        clearSearch();
        callSearchApi(nome).enqueue(new Callback<PersonagemResponse>() {
            @Override
            public void onResponse(Call<PersonagemResponse> call, Response<PersonagemResponse> response) {
                PersonagemResponse persons = response.body();

                List<Personagem> results = persons.getResults();
                adapter.clear();
                adapter.addAll(results);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                if (currentPage < TOTAL_PAGES)
                    adapter.addLoadingFooter();
                else {
                    isLastPage = true;
                }
            }

            @Override
            public void onFailure(Call<PersonagemResponse> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }

    protected void clearSearch() {
        isLoading = isLastPage = false;
        TOTAL_PAGES = 1;
        currentPage = PAGE_START;
    }

    private void loadFirstPage() {
        clearSearch();
        callPersonsApi().enqueue(new Callback<PersonagemResponse>() {
            @Override
            public void onResponse(Call<PersonagemResponse> call, Response<PersonagemResponse> response) {

                PersonagemResponse persons = response.body();

                List<Personagem> results =  persons.getResults();

                try {
                    TOTAL_PAGES = (int) Math.ceil((double) persons.getCount() / persons.getResults().size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressBar.setVisibility(View.GONE);
                adapter.clear();
                adapter.addAll(results);
                adapter.notifyDataSetChanged();
                if (currentPage <= TOTAL_PAGES)
                    adapter.addLoadingFooter();
                else {
                    isLastPage = true;
                }
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(results);
                realm.commitTransaction();
            }

            @Override
            public void onFailure(Call<PersonagemResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void loadNextPage() {

        callPersonsApi().enqueue(new Callback<PersonagemResponse>() {
            @Override
            public void onResponse(Call<PersonagemResponse> call, Response<PersonagemResponse> response) {
                adapter.removeLoadingFooter();
                isLoading = false;

                List<Personagem> results = fetchResults(response);
                adapter.addAll(results);
                adapter.notifyDataSetChanged();
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(results);
                realm.commitTransaction();
                realm.close();

                if (currentPage != TOTAL_PAGES)
                    adapter.addLoadingFooter();
                else
                    isLastPage = true;
            }

            @Override
            public void onFailure(Call<PersonagemResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //callSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                callSearch(newText);
                return true;
            }

            public void callSearch(String query) {
                if (!TextUtils.isEmpty(query))
                    loadSearch(query);
                else
                    loadFirstPage();
            }

        });
        return true;
    }

    private Call<PersonagemResponse> callPersonsApi() {
        return api.getResults(currentPage);
    }

    private Call<PersonagemResponse> callSearchApi(String name) {
        return api.searchPeople(name);
    }
}
