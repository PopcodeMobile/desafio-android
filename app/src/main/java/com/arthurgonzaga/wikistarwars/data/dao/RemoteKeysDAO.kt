package com.arthurgonzaga.wikistarwars.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arthurgonzaga.wikistarwars.data.model.RemoteKeys

@Dao
interface RemoteKeysDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(remoteKey: RemoteKeys)

    @Query("SELECT * FROM remote_keys WHERE characterId = :id")
    suspend fun remoteKeysById(id: Int): RemoteKeys?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()

    @Query("SELECT * FROM remote_keys")
    suspend fun getRemoteKeys(): List<RemoteKeys>
}