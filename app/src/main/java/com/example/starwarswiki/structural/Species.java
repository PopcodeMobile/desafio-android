package com.example.starwarswiki.structural;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "count",
        "next",
        "previous",
        "results"
})
/**
 * POJO Class to help with species response from SWAPI
 */
public class Species extends JsonSWAPIPage {

    @JsonProperty("results")
    private List<Specie> listOfSpecie = null;


    @JsonProperty("results")
    public List<Specie> getListOfSpecie() {
        return listOfSpecie;
    }

    @JsonProperty("results")
    public void setListOfSpecie(List<Specie> listOfSpecie) {
        this.listOfSpecie = listOfSpecie;
    }

}
