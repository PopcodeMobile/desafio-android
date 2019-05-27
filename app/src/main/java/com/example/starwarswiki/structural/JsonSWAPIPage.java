package com.example.starwarswiki.structural;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "count",
        "next",
        "previous"
})

/**
 * Root class to provide info that is common in all page requests
 */
public class JsonSWAPIPage {
    @JsonProperty("count")
    private Integer count;
    @JsonProperty("next")
    private String next;
    @JsonProperty("previous")
    private Object previous;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();



    @JsonProperty("count")
    public Integer getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(Integer count) {
        this.count = count;
    }

    @JsonProperty("next")
    public String getNext() {
        return next;
    }

    @JsonProperty("next")
    public void setNext(String next) {
        this.next = next;
    }

    @JsonProperty("previous")
    public Object getPrevious() {
        return previous;
    }

    @JsonProperty("previous")
    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
