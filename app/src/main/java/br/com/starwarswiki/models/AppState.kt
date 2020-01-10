package br.com.starwarswiki.models

data class AppState(
    val people: ServerResponse<Person>? = null,
    val planets: ServerResponse<Planet>? = null,
    val species: ServerResponse<Specie>? = null,

    val stateStarted: Boolean = true
)