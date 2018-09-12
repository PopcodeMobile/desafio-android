package com.android.vferreirati.starwarscharacters;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.vferreirati.starwarscharacters.adapters.PaginationAdapter;
import com.android.vferreirati.starwarscharacters.listeners.PaginationScrollListener;
import com.android.vferreirati.starwarscharacters.models.Character;
import com.android.vferreirati.starwarscharacters.models.PeopleQuery;
import com.android.vferreirati.starwarscharacters.services.CharacterApi;
import com.android.vferreirati.starwarscharacters.services.CharacterService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String KEY_CHARACTERS_LIST = "CharactersListKey";

    private PaginationAdapter mPaginationAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ProgressBar mProgressBar;
    DividerItemDecoration mItemDecoration;

    private static final int PAGE_START = 1;

    // Is loading more resources from server?
    private boolean isLoading = false;

    // Was the last page loaded from server already?
    private boolean isLastPage = false;

    // Number of pages to be loaded from server (Using mock data atm)
    private int TOTAL_PAGES;

    // Current page that is being fetched.
    private int mCurrentPage = PAGE_START;

    // Retrofit CharacterService
    private CharacterService mCharacterService;

    ArrayList<Character> mCharactersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.rc_characters);
        mProgressBar = findViewById(R.id.pb_loading);
        mPaginationAdapter = new PaginationAdapter(this);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mPaginationAdapter);
        mRecyclerView.addItemDecoration(mItemDecoration);
        mRecyclerView.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
            @Override
            public void loadMoreItems() {
                isLoading = true;
                mCurrentPage++;
                loadNextPage();
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

        mCharacterService = CharacterApi.getClient().create(CharacterService.class);

        // New instance?
        if (savedInstanceState == null) {
            mCharactersList = new ArrayList<>();
            loadFirstPage();
        } else {
            mProgressBar.setVisibility(View.GONE);
            mCharactersList = savedInstanceState.getParcelableArrayList(KEY_CHARACTERS_LIST);
            mPaginationAdapter.addAll(mCharactersList);
            Log.d(TAG, "Reusing character list data");
        }

    }

    private void loadFirstPage() {
        callPeopleQueryApi().enqueue(new Callback<PeopleQuery>() {
            @Override
            public void onResponse(Call<PeopleQuery> call, Response<PeopleQuery> response) {
                List<Character> characters = fetchCharacters(response);
                mProgressBar.setVisibility(View.GONE);
                mPaginationAdapter.addAll(characters);

                mCharactersList.addAll(characters);

                if (mCurrentPage <= TOTAL_PAGES) {
                    mPaginationAdapter.addLoadingFooter();
                } else {
                    isLastPage = true;
                }
            }

            @Override
            public void onFailure(Call<PeopleQuery> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error while making request", Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.getMessage());

                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void loadNextPage() {
        callPeopleQueryApi().enqueue(new Callback<PeopleQuery>() {
            @Override
            public void onResponse(Call<PeopleQuery> call, Response<PeopleQuery> response) {
                mPaginationAdapter.removeLoadingFooter();
                isLoading = false;

                List<Character> characters = fetchCharacters(response);
                mPaginationAdapter.addAll(characters);

                if (mCurrentPage != TOTAL_PAGES) {
                    mPaginationAdapter.addLoadingFooter();
                } else {
                    isLastPage = true;
                }
            }

            @Override
            public void onFailure(Call<PeopleQuery> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error while making request", Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.getMessage());

                mPaginationAdapter.removeLoadingFooter();
            }
        });


    }

    private Call<PeopleQuery> callPeopleQueryApi() {
        return mCharacterService.getCharacters(
                getString(R.string.query_format),
                mCurrentPage
        );
    }

    private List<Character> fetchCharacters(Response<PeopleQuery> response) {
        PeopleQuery peopleQuery = response.body();
        TOTAL_PAGES = peopleQuery.getCount() / 10;
        Log.d(TAG, response.toString());

        return peopleQuery.getResults();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (!mCharactersList.isEmpty()) {
            outState.putParcelableArrayList(KEY_CHARACTERS_LIST, mCharactersList);
        }
    }
}
