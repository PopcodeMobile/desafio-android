package br.albuquerque.data.dto

import com.google.gson.annotations.SerializedName

class ResponseFavorite(
    val error: String? = null,
    @SerializedName("error_message") val errorMessage: String? = null,
    val status: String? = null,
    val message: String? = null
)