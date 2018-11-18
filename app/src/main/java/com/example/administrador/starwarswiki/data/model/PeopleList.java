package com.example.administrador.starwarswiki.data.model;

import com.example.administrador.starwarswiki.data.model.StarWarsCharacter;

import java.util.List;

public class PeopleList {
    private  int count;
    private String next;
    private String previous;
    private List<StarWarsCharacter> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<StarWarsCharacter> getResults() {
        return results;
    }

    public void setResults(List<StarWarsCharacter> results) {
        this.results = results;
    }
}
