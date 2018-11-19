package com.example.administrador.starwarswiki.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.administrador.starwarswiki.data.model.StarWarsCharacter;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

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

    @Update(onConflict = IGNORE)
    void update(StarWarsCharacter starWarsCharacter);

    @Query("SELECT * FROM starwarscharacter WHERE id = :id")
    StarWarsCharacter load(int id);

    @Query("UPDATE starwarscharacter SET favorite = :favorite WHERE id = :id")
    void updateFavorite(boolean favorite, int id);
}