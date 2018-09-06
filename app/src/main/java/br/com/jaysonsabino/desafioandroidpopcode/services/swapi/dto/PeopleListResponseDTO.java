package br.com.jaysonsabino.desafioandroidpopcode.services.swapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PeopleListResponseDTO {

    private int count;
    private String next;
    private List<Character> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Character> getResults() {
        return results;
    }

    public void setResults(List<Character> results) {
        this.results = results;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
