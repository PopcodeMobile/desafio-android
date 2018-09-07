package br.com.jaysonsabino.desafioandroidpopcode.database;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;

@Dao
public interface CharacterDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Character> characters);

    @Query("DELETE FROM Character")
    void deleteAll();

    @Query("SELECT * FROM Character ORDER BY created")
    DataSource.Factory<Integer, Character> findAll();
}
