package com.matheusfroes.swapi.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.matheusfroes.swapi.data.model.Person

@Dao
interface PersonDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(people: List<Person>)

    @Query("SELECT * FROM people WHERE isBookmarked = 1")
    fun getBookmarkedPeople(): List<Person>

    @Query("SELECT * FROM people WHERE id = :personId")
    fun getPerson(personId: Long): Person

    @Query("UPDATE people SET isBookmarked = 1 WHERE id = :personId")
    fun bookmarkPerson(personId: Long)

    @Query("UPDATE people SET isBookmarked = 0 WHERE id = :personId")
    fun unbookmarkPerson(personId: Long)

    @Query("SELECT * FROM people WHERE name LIKE '%' || :name || '%'")
    fun searchPeopleByName(name: String): List<Person>

    @Query("SELECT * FROM people")
    fun getPeople(): List<Person>
}