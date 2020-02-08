package com.albuquerque.starwarswiki.model.dto

data class ResponsePeople(
    val count: Int? = null,
    val next: String? = null,
    val previous: String? = null,
    val results: ArrayList<People>? = null
)