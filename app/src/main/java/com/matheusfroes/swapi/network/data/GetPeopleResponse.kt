package com.matheusfroes.swapi.network.data

data class GetPeopleResponse(
        val count: Int,
        val next: String?,
        val previous: String?,
        val results: List<PersonResponse>
)