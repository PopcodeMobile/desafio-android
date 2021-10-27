package com.matheussilas97.starwarsapp.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(layoutManager: GridLayoutManager) :
    RecyclerView.OnScrollListener() {

    companion object {
        const val PAGE_START = 1
    }

    private val mlayoutManager: GridLayoutManager = layoutManager

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val totalItemCount = mlayoutManager.itemCount
        val lastVisibleItem = mlayoutManager.findLastVisibleItemPosition()


        if (lastVisibleItem == totalItemCount - 1
        ) {
            loadMoreItems()
        }

    }

    protected abstract fun loadMoreItems()
}