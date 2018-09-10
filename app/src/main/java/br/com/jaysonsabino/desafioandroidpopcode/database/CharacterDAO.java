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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<Character> characters);

    @Query("DELETE FROM Character")
    void deleteAll();

    @Query("SELECT * FROM Character LEFT JOIN FavoriteCharacter ON characterId = id ORDER BY created")
    DataSource.Factory<Integer, Character.CharacterWithFavorite> findAll();

    @Query("SELECT * FROM Character LEFT JOIN FavoriteCharacter ON characterId = id WHERE name LIKE :name ORDER BY created")
    DataSource.Factory<Integer, Character.CharacterWithFavorite> findByName(String name);

    @Query("SELECT * FROM Character LEFT JOIN FavoriteCharacter ON characterId = id WHERE characterId IS NOT NULL ORDER BY created")
    DataSource.Factory<Integer, Character.CharacterWithFavorite> findAllFavorites();

    @Query("SELECT * FROM Character LEFT JOIN FavoriteCharacter ON characterId = id WHERE name LIKE :name AND characterId IS NOT NULL ORDER BY created")
    DataSource.Factory<Integer, Character.CharacterWithFavorite> findAllFavoritesByName(String name);
}
