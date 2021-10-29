package com.matheussilas97.starwarsapp.api.response

import com.google.gson.annotations.SerializedName

data class FavoriteErrorResponse(

    @SerializedName("error")
    val error: String,

    @SerializedName("error_message")
    val errorMessage: String

)
