package br.com.challenge.android.starwarswiki.model.data.dto

import com.google.gson.annotations.SerializedName

data class ApiSpecie(
        @SerializedName("name")
        val name: String
)