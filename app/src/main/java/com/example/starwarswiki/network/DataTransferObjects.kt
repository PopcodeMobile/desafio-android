package com.example.starwarswiki.network

//import com.example.starwarswiki.database.DatabasePerson
//import com.example.starwarswiki.domain.PersonModel
import com.example.starwarswiki.database.DatabasePerson
import com.example.starwarswiki.domain.PersonModel
import com.example.starwarswiki.util.getObjectId
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import okhttp3.Response
import okhttp3.ResponseBody
import timber.log.Timber

@JsonClass(generateAdapter = true)
data class PlanetNetworkObject(val name: String)

@JsonClass(generateAdapter = true)
data class SpecieNetworkObject(val name: String)

@JsonClass(generateAdapter = true)
data class NetworkObject(
    val count: Int,
    val next: String?,
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
        val idValue = getObjectId(it.url, "https://swapi.co/api/people/")
        val dbPerson = DatabasePerson(
            url = it.url,
            id = idValue,
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
//        //Timber.d("Person ${dbPerson.id} transformed to dbmodel")
        dbPerson
    }
}

fun NetworkObject.asDomainModel(): List<PersonModel>{
    return results.map {
        val idValue = getObjectId(it.url, "https://swapi.co/api/people/")
        val personModel = PersonModel(
            url = it.url,
            id = idValue,
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
//        Timber.d("Person ${personModel.id} transformed to dbmodel")
        personModel
    }
}

@JsonClass(generateAdapter = true)
data class FavoriteNetworkObject(
    val status: String?,
    val message: String?,
    val error: String?,
    @Json(name = "error_message")
    val errorMessage: String?
    )