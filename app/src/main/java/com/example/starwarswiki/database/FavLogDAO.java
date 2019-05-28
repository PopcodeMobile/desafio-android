package com.example.starwarswiki.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.starwarswiki.structural.FavLogItem;

import java.util.List;

@Dao
public interface FavLogDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertLogEntry(FavLogItem favLogItem);

    @Query("SELECT * FROM favlog_table")
    LiveData<List<FavLogItem>> getAllFailedFavs();

    @Query("DELETE FROM favlog_table")
    void removeAllFavLog();
}
