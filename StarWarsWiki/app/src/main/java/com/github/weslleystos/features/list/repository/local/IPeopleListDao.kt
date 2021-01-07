package com.github.weslleystos.features.list.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.weslleystos.shared.entities.People

@Dao
interface IPeopleListDao {
    @Query("SELECT * FROM peoples ORDER BY id LIMIT :limit OFFSET :offset")
    fun getAll(offset: Int, limit: Int = 10): List<People>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg peoples: People)
}