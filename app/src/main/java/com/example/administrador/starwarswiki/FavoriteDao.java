package com.example.administrador.starwarswiki;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface FavoriteDao {
    @Insert(onConflict = REPLACE)
    void save(Favorite favorite);
    @Query("SELECT * FROM favorite WHERE starWarsCharacterId = :starWarsCharacterId")
    LiveData<Integer> load(int starWarsCharacterId);
    @Query("SELECT * FROM favorite")
    LiveData<List<Favorite>> getAllFavorites();
    @Query("DELETE FROM favorite WHERE starWarsCharacterId = :id")
    void deleteFavorite(int id);
}
