package br.com.example.starwars.data.entities


import com.google.gson.annotations.SerializedName

data class ApiListPeopleResponse(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("results")
    val people: List<ApiPeople>
)