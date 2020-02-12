package com.albuquerque.starwarswiki.app.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "config")
class ConfigEntity(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int = 1,
    @ColumnInfo(name = "count") var count: Int
)