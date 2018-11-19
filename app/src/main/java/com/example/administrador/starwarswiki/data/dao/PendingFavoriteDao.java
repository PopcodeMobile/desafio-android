package com.example.administrador.starwarswiki.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.administrador.starwarswiki.data.model.PendingFavorite;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface PendingFavoriteDao {

    @Insert(onConflict = REPLACE)
    long save(PendingFavorite pendingFavorite);

    @Query("SELECT * FROM pendingfavorite")
    List<PendingFavorite> selectAllPendingFavorites();

    @Query("DELETE FROM pendingfavorite WHERE id = :id")
    void deletePendingFavorite(int id);
}
