package br.com.starwarswiki.models

data class AppState(
    val people: List<Person>? = null,
    val planets: List<Planet>? = null,
    val species: List<Specie>? = null,

    val stateStarted: Boolean = true
)

