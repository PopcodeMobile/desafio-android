package com.matheussilas97.starwarsapp.api.response

import com.google.gson.annotations.SerializedName

data class ErrorModel(

    @SerializedName("detail")
    val detail: String

)