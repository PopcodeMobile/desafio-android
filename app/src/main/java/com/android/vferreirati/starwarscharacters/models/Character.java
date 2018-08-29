package com.android.vferreirati.starwarscharacters.models;

public class Character {

    private String mName;
    private int mHeight;
    private int mWeight;
    private String mGender;

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
}
