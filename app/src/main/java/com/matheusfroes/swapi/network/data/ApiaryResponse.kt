package com.matheusfroes.swapi.network.data

import com.google.gson.annotations.SerializedName

data class ApiaryResponse(
        val error: String,
        @SerializedName("error_message")
        val errorMessage: String
)