package com.android.vferreirati.starwarscharacters.listeners;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.vferreirati.starwarscharacters.interfaces.IPaginationScrollListener;

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener implements IPaginationScrollListener {

    public static final String TAG = "PaginationScrollListnr";

    LinearLayoutManager mLayoutManager;

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

            // Check if the recycler view is at the bottom
            if((visibleItemsCount + firstVisibleItemPosition) >= totalItemsCount
                    && firstVisibleItemPosition >= 0) {

                Log.d(TAG, "Request more data!");
                loadMoreItems();
            }
        }
    }
}
