package br.com.challenge.android.starwarswiki.view

import br.com.challenge.android.starwarswiki.model.domain.Person

interface PersonItemClickListener {
    fun onClick(person: Person)
}