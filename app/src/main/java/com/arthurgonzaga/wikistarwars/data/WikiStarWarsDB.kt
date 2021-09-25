package com.arthurgonzaga.wikistarwars.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arthurgonzaga.wikistarwars.data.dao.CharacterDAO
import com.arthurgonzaga.wikistarwars.data.model.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1)
abstract class WikiStarWarsDB: RoomDatabase() {

    abstract fun charactersDAO(): CharacterDAO

    companion object {
        const val NAME = "wiki_starwars_db"
    }
}