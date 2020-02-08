package com.albuquerque.starwarswiki.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.albuquerque.starwarswiki.model.entity.PeopleEntity

@Dao
interface WikiDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(peoples: List<PeopleEntity>)

    @Query("select * from people")
    fun getPeople(): LiveData<List<PeopleEntity>>

    @Query("delete from people")
    fun deleteAll()

    @Transaction
    suspend fun saveAllTransaction(people: List<PeopleEntity>, shouldClearTable: Boolean) {

        if (shouldClearTable)
            deleteAll()

        saveAll(people)

    }

}