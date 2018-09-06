package br.com.jaysonsabino.desafioandroidpopcode.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;

@Dao
public interface CharacterDAO {

    @Insert
    void insert(Character character);

    @Query("SELECT * FROM Character")
    List<Character> findAll();
}
