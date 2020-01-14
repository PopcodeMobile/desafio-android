package br.com.starwarswiki.models

import com.google.gson.annotations.SerializedName
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Specie(
    @Id(assignable = true) var _id: Long = 1,
    @SerializedName("name") val name: String = "",
    @SerializedName("url") val url: String = ""
)