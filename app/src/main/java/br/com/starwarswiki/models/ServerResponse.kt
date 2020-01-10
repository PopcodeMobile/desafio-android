package br.com.starwarswiki.models

import com.google.gson.annotations.SerializedName

data class ServerResponse<T>(
    @SerializedName("count") val count: Int = 0,
    @SerializedName("next") val next: String? = null,
    @SerializedName("previous") val previous: String? = null,
    @SerializedName("results") val results: List<T> = listOf()
)