package com.android.vferreirati.starwarscharacters;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.android.vferreirati.starwarscharacters.adapters.PaginationAdapter;
import com.android.vferreirati.starwarscharacters.listeners.PaginationScrollListener;
import com.android.vferreirati.starwarscharacters.models.Character;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PaginationAdapter mPaginationAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ProgressBar mProgressBar;

    private static final int PAGE_START = 0;

    // Is loading more resources from server?
    private boolean isLoading = false;

    // Was the last page loaded from server already?
    private boolean isLastPage = false;

    // Number of pages to be loaded from server (Using mock data atm)
    // TODO: Obtain this from server response when using actual data
    private int TOTAL_PAGES = 3;

    // Current page that is being fetched.
    private int mCurrentPage = PAGE_START;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.rc_characters);
        mProgressBar = findViewById(R.id.pb_loading);
        mPaginationAdapter = new PaginationAdapter(this);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mPaginationAdapter);
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

        // mocking 1 second network delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadFirstPage();
            }
        }, 1000);
    }

    private void loadFirstPage() {
        // TODO: Get actual data from swapi
        List<Character> characters = Character.createMockCharacters(mPaginationAdapter.getItemCount());
        mProgressBar.setVisibility(View.GONE);
        isLoading = false;

        mPaginationAdapter.addAll(characters);

        if (mCurrentPage <= TOTAL_PAGES) {
            mPaginationAdapter.addLoadingFooter();
        } else {
            isLastPage = true;
        }
    }

    private void loadNextPage() {
        List<Character> characters = Character.createMockCharacters(mPaginationAdapter.getItemCount());

        mPaginationAdapter.removeLoadingFooter();
        isLoading = false;

        mPaginationAdapter.addAll(characters);

        if(mCurrentPage != TOTAL_PAGES) {
            mPaginationAdapter.addLoadingFooter();
        } else {
            isLastPage = true;
        }
    }
}
