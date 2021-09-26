package com.arthurgonzaga.wikistarwars.data.dao

import androidx.paging.PagingSource
import com.arthurgonzaga.wikistarwars.data.model.CharacterEntity

import androidx.room.*


@Dao
interface CharacterDAO{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(characterEntity: List<CharacterEntity>): List<Long>

    @Query("SELECT id FROM characters WHERE is_favorite = 1")
    fun getAllFavoritesIds(): List<Int>

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): PagingSource<Int, CharacterEntity>

    @Query("DELETE FROM characters WHERE is_favorite = 0")
    fun clearCharacters()

    @Query(
 """
        UPDATE characters
            SET name=:name,
                height=:height, 
                weight=:weight, 
                hair_color=:hairColor, 
                skin_color=:skinColor, 
                eye_color=:eyeColor, 
                birth_year=:birthYear, 
                gender=:gender
            WHERE id = :id
        """
    )
    fun update(
        id: Int,
        name: String = "",
        height: String = "",
        weight: String = "",
        hairColor: String = "",
        skinColor: String = "",
        eyeColor: String = "",
        birthYear: String = "",
        gender: String = "",
    )

    @Transaction
    suspend fun upsert(obj: List<CharacterEntity>) {
        val favorites = getAllFavoritesIds()
        clearCharacters()

        insertAll(obj);

        favorites.forEach { id ->

            val character = obj.find { it.id == id }

            character?.let {
                update(
                    id = id,
                    name = character.name,
                    height = character.height,
                    weight = character.weight,
                    hairColor = character.hairColor,
                    skinColor = character.skinColor,
                    eyeColor = character.eyeColor,
                    birthYear = character.birthYear,
                    gender = character.gender,
                )
            }
        }
    }
}