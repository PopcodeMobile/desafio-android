package com.example.starwarswiki.network

//import com.example.starwarswiki.database.DatabasePerson
//import com.example.starwarswiki.domain.PersonModel
import com.example.starwarswiki.database.DatabasePerson
import com.example.starwarswiki.domain.PersonModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkObject(
    val count: Int,
    val results: List<NetworkPerson>
)

@JsonClass(generateAdapter = true)
data class NetworkPerson(
    val url: String,
    val name: String,
    val height: String,
    val mass: String,
    @Json(name = "hair_color")
    val hairColor: String,
    @Json(name = "skin_color")
    val skinColor: String,
    @Json(name = "eye_color")
    val eyeColor: String,
    @Json(name = "birth_year")
    val birthYear: String,
    val gender: String,
    val homeworld: String,
    val species: List<String>
)

fun NetworkObject.asDatabaseModel():List<DatabasePerson>{
    return results.map{
        DatabasePerson(
            url = it.url,
            name = it.name,
            height = it.height,
            mass = it.mass,
            gender = it.gender,
            homeworld = it.homeworld,
            hair_color = it.hairColor,
            skin_color = it.skinColor,
            eye_color = it.eyeColor,
            birth_year = it.birthYear,
            species = it.species
        )
    }
}

fun NetworkObject.asDomainModel(): List<PersonModel>{
    return results.map {
        PersonModel(
            url = it.url,
            name = it.name,
            height = it.height,
            mass = it.mass,
            gender = it.gender,
            homeworld = it.homeworld,
            hair_color = it.hairColor,
            skin_color = it.skinColor,
            eye_color = it.eyeColor,
            birth_year = it.birthYear,
            species = it.species
        )
    }
}