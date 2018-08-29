package com.android.vferreirati.starwarscharacters.models;

import java.util.ArrayList;
import java.util.List;

public class Character {

    private String mName;
    private int mHeight;
    private int mWeight;
    private String mGender;

    public Character() { }

    public Character(String name, int height, int weight, String gender) {
        mName = name;
        mHeight = height;
        mWeight = weight;
        mGender = gender;
    }

    public String getName() {
        return mName;
    }

    public int getHeight() {
        return mHeight;
    }

    public int getWeight() {
        return mWeight;
    }

    public String getGender() {
        return mGender;
    }

    // Creates a list of mock data
    public static List<Character> createMockCharacters(int itemCount) {
        List<Character> characters = new ArrayList<>();

        for(int i=0; i<15; i++) {
            String name = "Character #"+ (itemCount == 0 ? (itemCount + 1 + i) : (itemCount + i));
            int height = 170;
            int weight = 70;
            String gender = i % 2 == 0? "Male" : "Female";
            Character character = new Character(name, height, weight, gender);
            characters.add(character);
        }

        return characters;
    }
}
