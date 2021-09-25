package com.arthurgonzaga.wikistarwars.api.responses

import com.google.common.truth.Truth.assertThat
import com.google.gson.annotations.SerializedName
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CharacterResponseTest {

    @Test
    fun `should return null when the list is empty`() {

        // Given
        val characterResponse = CharacterResponse(
            speciesUrls = listOf(),
            homeWorldUrl = "planets/1/",
            url = "people/1/"
        )

        // When
        val specieId = characterResponse.specieId

        // Then
        assertThat(specieId).isEqualTo(null)
    }

    @Test
    fun `should return the id of the first index when the species list is not empty`() {

        val url1 = "https://swapi.dev/api/species/1/"
        val url2 = "https://swapi.dev/api/species/33/"

        // Given
        val characterResponse = CharacterResponse(
            speciesUrls = listOf(url1, url2),
            homeWorldUrl = "planets/1/",
            url = "people/1/"
        )

        // When
        val specieId = characterResponse.specieId

        // Then
        assertThat(specieId).isEqualTo(1)
    }


    @Test
    fun `should return the id of the planet`() {
        // Given
        val characterResponse =
            CharacterResponse(
                homeWorldUrl = "https://swapi.dev/api/planets/10/",
                url = "people/1/"
            )

        // When
        val planetId = characterResponse.homeWorldId

        // Then
        assertThat(planetId).isEqualTo(10)
    }

    @Test
    fun `should return the id of the character`() {
        // Given
        val characterResponse =
            CharacterResponse(
                homeWorldUrl = "planets/1/",
                url = "https://swapi.dev/api/people/45/"
            )

        // When
        val planetId = characterResponse.id

        // Then
        assertThat(planetId).isEqualTo(45)
    }
}