package com.matheussilas97.starwarsapp.api.response

import com.google.gson.annotations.SerializedName

data class CharactersListResponse(

    @SerializedName("count")
    val count: Int,

    @SerializedName("next")
    val next: String,

    @SerializedName("previous")
    val previous: String,

    @SerializedName("results")
    val results: List<CharactersDetailsResponse>

)