package com.arthurgonzaga.wikistarwars.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arthurgonzaga.wikistarwars.data.model.CharacterEntity

@Dao
interface CharacterDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(characterEntity: List<CharacterEntity>)


    @Query("SELECT * FROM characters")
    fun getAllCharacters(): PagingSource<Int, CharacterEntity>

    @Query("DELETE FROM characters")
    fun clearCharacters()
}