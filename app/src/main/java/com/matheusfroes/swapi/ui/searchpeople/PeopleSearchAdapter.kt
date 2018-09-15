package com.matheusfroes.swapi.ui.searchpeople

import android.arch.paging.PagedListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.matheusfroes.swapi.R
import com.matheusfroes.swapi.data.model.Person
import com.matheusfroes.swapi.ui.peoplelist.PeopleAdapter
import com.matheusfroes.swapi.ui.peoplelist.PersonClickEvent
import kotlinx.android.synthetic.main.person_search_view.view.*

class PeopleSearchAdapter
    : PagedListAdapter<Person, PeopleSearchAdapter.PeopleViewHolder>(PeopleAdapter.PERSON_DIFF) {
    var personClickEvent: PersonClickEvent? = null

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PeopleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.person_search_view, parent, false)
        return PeopleViewHolder(view)
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        val person = getItem(position)

        holder.bind(person)
    }

    inner class PeopleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                personClickEvent?.invoke(getItem(adapterPosition)?.id ?: 0)
            }
        }

        fun bind(person: Person?) {
            if (person != null) {
                itemView.tvPersonName.text = person.name
                itemView.tvPersonGender.text = person.gender
                itemView.tvPersonHeight.text = person.height
                itemView.tvPersonMass.text = person.mass
            }

        }
    }
}