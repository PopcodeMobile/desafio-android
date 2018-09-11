package com.matheusfroes.swapi.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "species")
data class Specie(
        @PrimaryKey
        val id: Long,
        val name: String
)