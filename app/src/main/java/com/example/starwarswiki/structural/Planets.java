package com.example.starwarswiki.structural;

/**
 * POJO Class to help with planets response from SWAPI
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "count",
        "next",
        "previous",
        "results"
})
/**
 * POJO Class to help with planets response from SWAPI
 */
public class Planets extends JsonSWAPIPage {

    @JsonProperty("results")
    private List<Planet> listOfPlanet = null;


    @JsonProperty("results")
    public List<Planet> getListOfPlanet() {
        return listOfPlanet;
    }

    @JsonProperty("results")
    public void setListOfPlanets(List<Planet> listOfPlanet) {
        this.listOfPlanet = listOfPlanet;
    }

}