package com.knowledge.wikisw_luan.data.cache

import androidx.room.*

@Dao
interface CharacterDAO {
    @Query("SELECT * FROM sw_chars")
    fun getAll(): List<CharacterEntity>

    @Query("SELECT * FROM sw_chars where cid == (:id)")
    fun loadCharacterById(id: String): CharacterEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(a: List<CharacterEntity>)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(fav: CharacterEntity)

}