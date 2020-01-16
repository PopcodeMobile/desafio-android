package br.com.starwarswiki.models

import br.com.starwarswiki.models.converters.ListStringConverter
import com.google.gson.annotations.SerializedName
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Person(
    @Id(assignable = true) var _id: Long = 1,
    @SerializedName("name") val name: String = "",
    @SerializedName("height") val height: String = "",
    @SerializedName("mass") val mass: String = "",
    @SerializedName("hair_color") val hair_color: String = "",
    @SerializedName("skin_color") val skin_color: String = "",
    @SerializedName("eye_color") val eye_color: String = "",
    @SerializedName("birth_year") val birth_year: String = "",
    @SerializedName("gender") val gender: String = "",
    val isFavorite: Boolean = false,
    @SerializedName("homeworld") val homeworld: String = "",

    @Convert(converter = ListStringConverter::class, dbType = String::class)
    val species: List<String> = listOf()
)