package br.com.example.starwars.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.example.starwars.data.entities.RemoteKeys

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRemoteKeys(redditKey: RemoteKeys)

    @Query("SELECT * FROM remote_keys ORDER BY id DESC")
    suspend fun getRemoteKeys(): List<RemoteKeys>
}