package com.matheusfroes.swapi.network.data

import com.google.gson.annotations.SerializedName

data class ApiarySuccessResponse(
        val status: String,
        val message: String
)

data class ApiaryFailureResponse(
        val error: String,
        @SerializedName("error_message")
        val errorMessage: String
)
