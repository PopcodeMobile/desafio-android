package com.arthurgonzaga.wikistarwars.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arthurgonzaga.wikistarwars.data.dao.CharacterDAO
import com.arthurgonzaga.wikistarwars.data.dao.RemoteKeysDAO
import com.arthurgonzaga.wikistarwars.data.model.CharacterEntity
import com.arthurgonzaga.wikistarwars.data.model.RemoteKeys

@Database(entities = [CharacterEntity::class, RemoteKeys::class], version = 1)
abstract class WikiStarWarsDB: RoomDatabase() {

    abstract fun charactersDAO(): CharacterDAO
    abstract fun remoteKeysDAO(): RemoteKeysDAO

    companion object {
        const val NAME = "wiki_starwars_db"
    }
}