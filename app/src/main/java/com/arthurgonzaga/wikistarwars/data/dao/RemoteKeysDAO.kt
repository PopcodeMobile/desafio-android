package com.arthurgonzaga.wikistarwars.data.dao

import androidx.room.*
import com.arthurgonzaga.wikistarwars.data.model.CharacterEntity
import com.arthurgonzaga.wikistarwars.data.model.RemoteKeys

@Dao
interface RemoteKeysDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(remoteKey: List<RemoteKeys>): List<Long>

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateAll(remoteKey: List<RemoteKeys>)


    @Query("SELECT * FROM remote_keys WHERE characterId = :id")
    fun remoteKeysById(id: Int): RemoteKeys?

    @Query("DELETE FROM remote_keys")
    fun clearRemoteKeys()



    @Transaction
    fun upsert(remoteKey: List<RemoteKeys>) {
        val longs: List<Long> = insertAll(remoteKey)

        if (longs.contains(-1)) {
            updateAll(remoteKey)
        }
    }
}