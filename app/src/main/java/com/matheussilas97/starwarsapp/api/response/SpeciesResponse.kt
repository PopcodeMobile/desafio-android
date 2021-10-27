package com.matheussilas97.starwarsapp.api.response

import com.google.gson.annotations.SerializedName

data class SpeciesResponse(

    @SerializedName("name")
    val name: String

)
