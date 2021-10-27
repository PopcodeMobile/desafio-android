package com.matheussilas97.starwarsapp.api.response

import com.google.gson.annotations.SerializedName

data class HomeWorldResponse(

    @SerializedName("name")
    val name: String

)