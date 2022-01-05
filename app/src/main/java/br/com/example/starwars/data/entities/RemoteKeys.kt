package br.com.example.starwars.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey(autoGenerate = true)
    val repoId: Long? = 0,
    val prevKey: Int?,
    val nextKey: Int?
)