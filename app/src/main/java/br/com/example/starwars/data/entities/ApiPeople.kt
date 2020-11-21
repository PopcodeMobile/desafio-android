package br.com.example.starwars.data.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.example.starwars.domain.entities.People
import com.google.gson.annotations.SerializedName

@Entity(tableName = "people")
data class ApiPeople(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @SerializedName("gender")
    val gender: String? = null,
    @SerializedName("height")
    val height: String? = null,
    @SerializedName("mass")
    val mass: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("hair_color")
    val hairColor: String? = null,
    @SerializedName("skin_color")
    val skinColor: String? = null,
    @SerializedName("eye_color")
    val eyeColor: String? = null,
    @SerializedName("birth_year")
    val birthYear: String? = null,
    @SerializedName("homeworld")
    val homeWorld: String? = null,
    @SerializedName("species")
    val species: List<String>? = null,
    val favorite: Boolean = false
) {

    fun apiPeopleToPeople(apiPeople: ApiPeople): People {
        return People(
            id = apiPeople.id,
            name = apiPeople.name,
            height = apiPeople.height,
            gender = apiPeople.gender,
            mass = apiPeople.mass,
            url = apiPeople.url,
            hairColor = apiPeople.hairColor,
            skinColor = apiPeople.skinColor,
            eyeColor = apiPeople.eyeColor,
            birthYear = apiPeople.birthYear,
            homeWorld = apiPeople.homeWorld,
            species = apiPeople.species,
            favorite = apiPeople.favorite
        )
    }
}