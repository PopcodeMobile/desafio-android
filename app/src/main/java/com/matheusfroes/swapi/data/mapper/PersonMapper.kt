package com.matheusfroes.swapi.data.mapper

import com.matheusfroes.swapi.data.model.Person
import com.matheusfroes.swapi.extractIdFromUrl
import com.matheusfroes.swapi.network.data.PersonResponse

class PersonMapper {
    companion object {
        fun map(personResponse: PersonResponse, species: List<String>, homeworld: String): Person {
            return Person(
                    id = extractIdFromUrl(personResponse.url),
                    name = personResponse.name,
                    height = personResponse.height,
                    mass = personResponse.mass,
                    hairColor = personResponse.hairColor,
                    skinColor = personResponse.skinColor,
                    eyeColor = personResponse.eyeColor,
                    birthYear = personResponse.birthYear,
                    gender = personResponse.gender,
                    homeworld = homeworld,
                    species = species.joinToString(),
                    isBookmarked = false
            )
        }
    }
}