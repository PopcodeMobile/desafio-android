package com.example.desafio_android.data.model

import com.google.gson.annotations.SerializedName

data class ApiFavoriteResponse(
    @SerializedName("error")
    val error: String,
    @SerializedName("error_message")
    val errorMessage: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)