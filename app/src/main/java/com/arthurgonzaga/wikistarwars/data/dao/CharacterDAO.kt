package com.arthurgonzaga.wikistarwars.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.arthurgonzaga.wikistarwars.data.model.CharacterEntity

@Dao
interface CharacterDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(characterEntity: List<CharacterEntity>)

}