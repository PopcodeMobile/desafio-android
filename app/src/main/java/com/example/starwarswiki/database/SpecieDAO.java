package com.example.starwarswiki.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.starwarswiki.structural.Specie;

import java.util.List;

@Dao
public interface SpecieDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public  void insertSpecie(Specie specie);

    @Query("DELETE FROM specie_table")
    void removeAllSpecie();

    @Query("SELECT * FROM specie_table WHERE url = :name")
    public LiveData<List<Specie>> getHomeworld(String name );

    @Query("SELECT * FROM specie_table ORDER BY CAST ( url AS INTEGER )")
    public LiveData<List<Specie>> getAllSpecie();
}
