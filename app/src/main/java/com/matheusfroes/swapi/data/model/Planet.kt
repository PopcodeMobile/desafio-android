package com.matheusfroes.swapi.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "planets")
data class Planet(
        @PrimaryKey
        val id: Long,
        val name: String
)