package com.example.starwarswiki.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PersonDao {
    @Query("SELECT * FROM table_person")
    fun getAllPeople(): LiveData<List<DatabasePerson>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(persons: List<DatabasePerson>)
    @Query("DELETE FROM table_person")
    fun clear()
}

