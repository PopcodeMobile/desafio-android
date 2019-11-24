package com.example.starwarswiki.network

//import com.example.starwarswiki.database.DatabasePerson
//import com.example.starwarswiki.domain.PersonModel
//import com.squareup.moshi.Json
//import com.squareup.moshi.JsonClass
//import kotlinx.android.parcel.Parcelize
//
//@JsonClass(generateAdapter = true)
//data class NetworkPersonContainer(val personList: List<NetworkPerson>)
//
//@JsonClass(generateAdapter = true)
//data class NetworkPerson(
//    val url: String,
//    val name: String,
//    val height: String,
//    val mass: String,
//    @Json(name = "hair_color")
//    val hairColor: String,
//    @Json(name = "skin_color")
//    val skinColor: String,
//    @Json(name = "eye_color")
//    val eyeColor: String,
//    @Json(name = "birth_year")
//    val birthYear: String,
//    val gender: String,
//    val homeworld: String,
//    val species: List<String>
//)
//
//fun NetworkPersonContainer.asDatabaseModel():List<DatabasePerson>{
//    return personList.map{
//        DatabasePerson(
//            url = it.url,
//            name = it.name,
//            height = it.height,
//            mass = it.mass,
//            gender = it.gender,
//            homeworld = it.homeworld,
//            hair_color = it.hairColor,
//            skin_color = it.skinColor,
//            eye_color = it.eyeColor,
//            birth_year = it.birthYear,
//            species = it.species
//        )
//    }
//}
//
//fun NetworkPersonContainer.asDomainModel(): List<PersonModel>{
//    return personList.map {
//        PersonModel(
//            url = it.url,
//            name = it.name,
//            height = it.height,
//            mass = it.mass,
//            gender = it.gender,
//            homeworld = it.homeworld,
//            hair_color = it.hairColor,
//            skin_color = it.skinColor,
//            eye_color = it.eyeColor,
//            birth_year = it.birthYear,
//            species = it.species
//        )
//    }
//}