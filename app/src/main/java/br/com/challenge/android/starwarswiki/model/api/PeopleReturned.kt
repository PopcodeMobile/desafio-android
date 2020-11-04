package br.com.challenge.android.starwarswiki.model.api

import br.com.challenge.android.starwarswiki.model.data.dto.ApiPerson
import com.google.gson.annotations.SerializedName

data class PeopleReturned(
    val results: ArrayList<ApiPerson>
)