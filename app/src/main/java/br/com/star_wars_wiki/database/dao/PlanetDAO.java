package br.com.star_wars_wiki.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import br.com.star_wars_wiki.entity.Planet;

@Dao
public interface PlanetDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Planet planet);

    @Query("SELECT * FROM Planet")
    LiveData<List<Planet>> getAllPlanets();
}
