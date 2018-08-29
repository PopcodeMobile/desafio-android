package com.android.vferreirati.starwarscharacters.interfaces;

/*
* Interface which contains help logic to implement
* a Scroll Listener which supports data Pagination
 */
public interface IPaginationScrollListener {

    void loadMoreItems();
    int getTotalPageCount();
    boolean isLastPage();
    boolean isLoading();
}
