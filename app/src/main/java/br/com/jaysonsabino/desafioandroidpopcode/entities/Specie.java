package br.com.jaysonsabino.desafioandroidpopcode.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Specie {

    private static final String SPECIE_URL_PATTERN = "https://swapi.co/api/species/([0-9]+)/";

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Integer getIdByUrl(String url) {
        Pattern pattern = Pattern.compile(SPECIE_URL_PATTERN);
        Matcher matcher = pattern.matcher(url);

        if (!matcher.find()) {
            return null;
        }

        String id = matcher.group(1);
        return Integer.parseInt(id);
    }
}
