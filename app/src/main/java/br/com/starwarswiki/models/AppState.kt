package br.com.starwarswiki.models

data class AppState(
    val people: Map<String, Person> = LinkedHashMap(),
    val planets: Map<String, Planet> = LinkedHashMap(),
    val species: Map<String, Specie> = LinkedHashMap(),

    val syncRunning: Boolean = false
) {
    fun getHomeWorld(state: AppState, person: Person?): String =
        state.planets.values.firstOrNull { it.url == person?.homeworld }?.name ?: ""

    fun getSpecies(state: AppState, person: Person?): String {
        var specie: Specie? = null

        person?.species?.forEach { item ->
            specie = state.species.values.firstOrNull {it.url == item}
        }

        return specie?.name ?: ""
    }
}

