package com.android.vferreirati.starwarscharacters.interfaces;

import java.util.List;

/*
* Interface which contains all methods required to
* implement pagination
* */
public interface IPaginationAdapter<T> {

    void add(T item);
    void addAll(List<T> items);
    boolean isEmpty();
    void remove(T item);
    void clear();
    void addLoadingFooter();
    void removeLoadingFooter();
}
