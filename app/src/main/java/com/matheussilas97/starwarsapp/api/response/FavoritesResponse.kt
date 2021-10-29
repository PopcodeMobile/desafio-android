package com.matheussilas97.starwarsapp.api.response

import com.google.gson.annotations.SerializedName

data class FavoritesResponse(

    @SerializedName("status")
    val status: String,

    @SerializedName("message")
    val message: String

)
