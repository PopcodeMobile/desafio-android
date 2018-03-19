package com.example.lucvaladao.entrevistapopcode.db;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.lucvaladao.entrevistapopcode.entity.Character;

import java.util.List;

/**
 * Created by lucvaladao on 3/19/18.
 */

interface CharacterDao {

    @Query("SELECT * FROM character")
    List<Character> getAllCharacters();

    @Insert
    void insert(Character character);

    @Delete
    void delete(Character character);
}
