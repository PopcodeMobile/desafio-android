package com.example.starwarswiki.database

import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.starwarswiki.domain.PersonModel

//@Entity
//data class DatabasePerson constructor(
//    @PrimaryKey
//    val url: String,
//    val name: String,
//    val height: String,
//    val mass: String,
//    val hair_color: String,
//    val skin_color: String,
//    val eye_color: String,
//    val birth_year: String,
//    val gender: String,
//    val homeworld: String,
//    val species: List<String>
//)
//
//fun List<DatabasePerson>.asDomainModel(): List<PersonModel>{
//    return map {
//        PersonModel(
//            url = it.url,
//            name = it.name,
//            height = it.height,
//            mass = it.mass,
//            hair_color = it.hair_color,
//            skin_color = it.skin_color,
//            eye_color = it.eye_color,
//            birth_year = it.birth_year,
//            gender = it.gender,
//            homeworld = it.homeworld,
//            species = it.species
//        )
//    }
//}