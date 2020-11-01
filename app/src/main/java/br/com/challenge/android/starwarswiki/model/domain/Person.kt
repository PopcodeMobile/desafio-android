package br.com.challenge.android.starwarswiki.model.domain

data class Person(
        val name: String,
        val birthYear: String,
        val eyeColor: String,
        val gender: String,
        val hairColor: String,
        val height: String,
        val mass: String,
        val skinColor: String,
        val homeWorld: String,
        val species: ArrayList<String>
)