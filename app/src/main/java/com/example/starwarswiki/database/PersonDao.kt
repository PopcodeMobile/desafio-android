package com.example.starwarswiki.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarswiki.domain.PersonModel
import kotlinx.coroutines.Deferred

@Dao
interface PersonDao {
    @Query("SELECT * FROM table_person WHERE name LIKE '%' || :keyName || '%'")
    fun getSearch(keyName:String?): List<DatabasePerson>?
    @Query("SELECT * FROM table_person WHERE id= :key")
    fun getPerson(key: Int): PersonModel?
    @Query("SELECT * FROM table_person")
    fun getAllPeople(): LiveData<List<DatabasePerson>>
    @Query("UPDATE table_person SET isFavorite = :status WHERE id = :key")
    fun updateFavorite(key: Int, status: Boolean)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(persons: List<DatabasePerson>)
    @Query("DELETE FROM table_person")
    fun clear()
}

