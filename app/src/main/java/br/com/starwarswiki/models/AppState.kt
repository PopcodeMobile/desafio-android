package br.com.starwarswiki.models

data class AppState(
    val people: List<Person>? = null,
    val planets: List<Planet>? = null,
    val species: List<Specie>? = null,

    val stateStarted: Boolean = true
) {
    fun synced() = people?.isNotEmpty()

    fun getHomeWorld(state: AppState, person: Person?): String =
        state.planets?.firstOrNull { it.url == person?.homeworld }?.name ?: ""

    fun getSpecies(state: AppState, person: Person?): String {
        var specie: Specie? = null

        person?.species?.forEach { item ->
            specie = state.species?.firstOrNull {it.url == item}
        }

        return specie?.name ?: ""
    }
}

