package com.example.starwarswiki.handlers;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.starwarswiki.structural.Planet;

import java.util.List;

@Dao
public interface PlanetDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertPlanet(Planet planet);

    @Query("DELETE FROM planet_table")
    void removeAllPlanet();

    @Query("SELECT * FROM planet_table WHERE url = :homeworldURL")
    public LiveData<List<Planet>> getHomeworld(String homeworldURL );

    @Query("SELECT * FROM planet_table ORDER BY CAST ( url AS INTEGER )")
    public LiveData<List<Planet>> getAllPlanets();
}
