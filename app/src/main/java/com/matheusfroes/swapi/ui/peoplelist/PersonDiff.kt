package com.matheusfroes.swapi.ui.peoplelist

import android.support.v7.util.DiffUtil
import com.matheusfroes.swapi.data.model.Person

class PersonDiff : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem == newItem
    }

}