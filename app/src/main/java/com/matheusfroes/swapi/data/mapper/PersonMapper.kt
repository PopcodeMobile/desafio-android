package com.matheusfroes.swapi.data.mapper

import com.matheusfroes.swapi.data.model.Person
import com.matheusfroes.swapi.data.model.Planet
import com.matheusfroes.swapi.data.model.Specie
import com.matheusfroes.swapi.extractIdFromUrl
import com.matheusfroes.swapi.network.data.PersonResponse

class PersonMapper {
    companion object {
        fun map(personResponse: PersonResponse, species: List<Specie>, planet: Planet): Person {
            val person = Person(
                    id = extractIdFromUrl(personResponse.url),
                    name = personResponse.name,
                    height = personResponse.height,
                    mass = personResponse.mass,
                    hairColor = personResponse.hairColor,
                    skinColor = personResponse.skinColor,
                    eyeColor = personResponse.eyeColor,
                    birthYear = personResponse.birthYear,
                    gender = personResponse.gender,
                    planet = planet,
                    isBookmarked = false
            )

            person.species = species

            return person
        }
    }
}