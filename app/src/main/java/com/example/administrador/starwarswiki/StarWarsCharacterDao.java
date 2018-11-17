package com.example.administrador.starwarswiki;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.FAIL;
import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface StarWarsCharacterDao {
    @Insert(onConflict = IGNORE)
    long save(StarWarsCharacter starWarsCharacter);
    @Query("SELECT * FROM starwarscharacter WHERE name = :characterName")
    LiveData<StarWarsCharacter> load(String characterName);
    @Query("SELECT * FROM starwarscharacter")
    LiveData<List<StarWarsCharacter>> getAllCharacters();
    @Query("SELECT * FROM starwarscharacter WHERE id = :id")
    LiveData<StarWarsCharacter> getCharcter(int id);
    //@Query("SELECT * FROM starwarscharacter WHERE id BETWEEN :v1 and :v2")
    //LiveData<List<StarWarsCharacter>> getCharactersRange(int v1, int v2);
    //@Query("SELECT count(1) FROM starwarscharacter WHERE id = :characterId ")
    //int checkExist(int characterId);
    @Update(onConflict = IGNORE)
    void update(StarWarsCharacter starWarsCharacter);
    @Query("SELECT * FROM starwarscharacter WHERE id = :id")
    StarWarsCharacter load(int id);
    @Query("UPDATE starwarscharacter SET favorite = :favorite WHERE id = :id")
    void updateFavorite(boolean favorite, int id);
}