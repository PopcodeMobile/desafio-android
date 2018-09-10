package br.com.jaysonsabino.desafioandroidpopcode.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.jaysonsabino.desafioandroidpopcode.entities.FavoriteCharacter;

@Dao
public interface FavoriteCharacterDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(FavoriteCharacter favoriteCharacter);

    @Update
    void update(FavoriteCharacter favoriteCharacter);

    @Delete
    void delete(FavoriteCharacter favoriteCharacter);

    @Query("SELECT * FROM FavoriteCharacter WHERE characterId = :characterId")
    FavoriteCharacter getByCharacterId(int characterId);

    @Query("SELECT * FROM FavoriteCharacter WHERE syncedWithApi = 0")
    List<FavoriteCharacter> findNotSynced();
}
