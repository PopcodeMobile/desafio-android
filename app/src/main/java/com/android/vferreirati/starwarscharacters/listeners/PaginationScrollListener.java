package com.android.vferreirati.starwarscharacters.listeners;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.vferreirati.starwarscharacters.interfaces.IPaginationScrollListener;

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener implements IPaginationScrollListener {

    public static final String TAG = "PaginationScrollListnr";

    LinearLayoutManager mLayoutManager;
    // Load new data when reaching the 70% mark => (TotalItems * 70) / 100
    private static final int mThresholdDivider = 7;

    // Number of items to start loading more data.
    private int mThreshold;

    public PaginationScrollListener(LinearLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemsCount = mLayoutManager.getChildCount(); // Visible amount of items on screen
        int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();  // Position of the first visible item
        int totalItemsCount = mLayoutManager.getItemCount();

        // If not on last page and not loading already
        if(!isLastPage() && !isLoading()) {

            // Limite = 70% of total items
            // Meaning it will load more items when the 70% mark is hit
            mThreshold = (totalItemsCount * 7) / 10;
            // Check if the recycler view is at the threshold
            if((visibleItemsCount + firstVisibleItemPosition) >= mThreshold
                    && firstVisibleItemPosition >= 0) {

                Log.d(TAG, "Request more data!");
                loadMoreItems();
            }
        }
    }
}
