package com.popcode.starwars.data.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.popcode.starwars.data.local.entity.CharacterElement;

import java.util.List;

@Dao
public interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CharacterElement character);

    @Query("DELETE FROM character_table")
    void deleteAll();

    @Query("SELECT * from character_table")
    LiveData<List<CharacterElement>> getAllCharacters();

    @Query("SELECT * from character_table WHERE name = :characterName")
    LiveData<CharacterElement> getCharacter(String characterName);

    @Query("SELECT * from character_table WHERE id = :id")
    LiveData<CharacterElement> getCharacterById(Integer id);
}
