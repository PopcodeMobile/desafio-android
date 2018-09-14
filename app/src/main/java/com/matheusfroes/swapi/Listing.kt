package com.matheusfroes.swapi

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.matheusfroes.swapi.extra.Result

/**
 * Data class that is necessary for a UI to show a listing and interact w/ the rest of the system
 */
data class Listing<T>(
        // the LiveData of paged lists for the UI to observe
        val pagedList: LiveData<PagedList<T>>,
        // represents the network request status to show to the user
        val networkState: LiveData<Result<Any>>)