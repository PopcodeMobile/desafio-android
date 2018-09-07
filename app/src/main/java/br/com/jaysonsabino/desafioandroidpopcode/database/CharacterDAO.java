package br.com.jaysonsabino.desafioandroidpopcode.database;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;

@Dao
public interface CharacterDAO {

    @Insert
    void insert(Character character);

    @Insert
    void insert(List<Character> characters);

    @Query("DELETE FROM Character WHERE Id IN (1, 2)")
    void deleteSomeForTestPurposes();

    @Query("DELETE FROM Character")
    void deleteAll();

    @Query("SELECT * FROM Character")
    List<Character> findAll();

    @Query("SELECT * FROM Character")
    DataSource.Factory<Integer, Character> loadCharactersByNameAsc();
}
