package com.albuquerque.starwarswiki.app.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.albuquerque.starwarswiki.app.model.entity.PersonEntity

@Dao
interface WikiDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(people: List<PersonEntity>)

    @Query("select * from people")
    fun getPeople(): LiveData<List<PersonEntity>>

    @Query("select * from people")
    suspend fun getPeopleSuspend(): List<PersonEntity>

    @Query("delete from people")
    fun deleteAll()

    @Update
    suspend fun updatePerson(person: PersonEntity)

    @Transaction
    suspend fun saveAllTransaction(people: List<PersonEntity>, shouldClearTable: Boolean) {

        if (shouldClearTable)
            deleteAll()

        saveAll(people)

    }

}