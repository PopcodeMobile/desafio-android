package com.example.starwars.model

import com.example.starwars.data.room.ResultEntity

data class People(
        val next: String,
        val results: List<ResultEntity>
)