package com.matheusfroes.swapi.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.matheusfroes.swapi.data.model.PendingBookmark
import com.matheusfroes.swapi.data.model.Person

@Dao
interface PersonDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(people: List<Person>)

    @Query("SELECT * FROM people WHERE isBookmarked = 1")
    fun getBookmarkedPeople(): LiveData<List<Person>>

    @Query("SELECT * FROM people WHERE id = :personId")
    fun getPerson(personId: Long): Person

    @Query("UPDATE people SET isBookmarked = 1 WHERE id = :personId")
    fun bookmarkPerson(personId: Long)

    @Query("UPDATE people SET isBookmarked = 0 WHERE id = :personId")
    fun unbookmarkPerson(personId: Long)

    @Query("SELECT * FROM people")
    fun getPeople(): LiveData<List<Person>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addPendingBookmark(pendingBookmark: PendingBookmark)

    @Query("SELECT * FROM pending_bookmarks")
    fun getPendingBookmarks(): List<PendingBookmark>

    @Query("DELETE FROM pending_bookmarks WHERE personId = :personId")
    fun removePendingBookmark(personId: Long)
}