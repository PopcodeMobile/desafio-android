package com.example.starwarswiki.structural;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "name",
        "url"
})

/**
 * Due to project specifications the class planet was simplified
 */
@Entity(tableName = "planet_table")
public class Planet {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "url")
    @JsonProperty("url")
    private String url;
    @JsonProperty("name")
    @ColumnInfo(name = "name")
    private String name;

    @JsonProperty("url")
    public String getUrl() { return url; }

    @JsonProperty("url")
    public void setUrl(String url) { this.url = url; }

    @JsonProperty("name")
    public String getName() { return name; }

    @JsonProperty("name")
    public void setName(String name) { this.name = name; }
}


