package app.com.wikistarwars;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import app.com.wikistarwars.Adapter.PaginationAdapter;
import app.com.wikistarwars.Api.RetrofitConfig;
import app.com.wikistarwars.Api.Service;
import app.com.wikistarwars.Model.Personagem;
import app.com.wikistarwars.Model.PersonagemResponse;
import app.com.wikistarwars.Util.PaginationScrollListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Personagem> personagemList;
    private ProgressDialog pDialog;
    private ListPersonagemAdapter eAdapter;

    private PaginationAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 1;
    private int currentPage = PAGE_START;

    private Service api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rvPersonagens);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        adapter = new PaginationAdapter(this);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

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

        private void loadFirstPage() {

            callPersonsApi().enqueue(new Callback<PersonagemResponse>() {
                @Override
                public void onResponse(Call<PersonagemResponse> call, Response<PersonagemResponse> response) {
                    // Got data. Send it to adapter
                    PersonagemResponse persons = response.body();
                    List<Personagem> results = persons.getResults();

                    try {
                        TOTAL_PAGES = (int) Math.ceil((double)persons.getCount()/persons.getResults().size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    progressBar.setVisibility(View.GONE);
                    adapter.addAll(results);

                    if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
                    else isLastPage = true;
                }

                @Override
                public void onFailure(Call<PersonagemResponse> call, Throwable t) {
                    t.printStackTrace();

                }
            });

        }

        private List<Personagem> fetchResults(Response<PersonagemResponse> response) {
            PersonagemResponse persons = response.body();
            return persons.getResults();
        }

        private void loadNextPage() {

            callPersonsApi().enqueue(new Callback<PersonagemResponse>() {
                @Override
                public void onResponse(Call<PersonagemResponse> call, Response<PersonagemResponse> response) {
                    adapter.removeLoadingFooter();
                    isLoading = false;

                    List<Personagem> results = fetchResults(response);
                    adapter.addAll(results);
                    if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                    else isLastPage = true;
                }

                @Override
                public void onFailure(Call<PersonagemResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }


        private Call<PersonagemResponse> callPersonsApi() {
            return api.getResults(currentPage);
        }



}
