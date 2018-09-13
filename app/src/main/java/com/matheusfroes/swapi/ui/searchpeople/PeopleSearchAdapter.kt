package com.matheusfroes.swapi.ui.searchpeople

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.matheusfroes.swapi.R
import com.matheusfroes.swapi.data.model.Person
import com.matheusfroes.swapi.ui.peoplelist.PersonClickEvent
import kotlinx.android.synthetic.main.person_search_view.view.*

class PeopleSearchAdapter
    : RecyclerView.Adapter<PeopleSearchAdapter.PeopleViewHolder>() {
    var items = mutableListOf<Person>()
        set(value) {
            field.addAll(value)
            notifyDataSetChanged()
        }

    var personClickEvent: PersonClickEvent? = null

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PeopleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.person_search_view, parent, false)
        return PeopleViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        val person = items[position]

        holder.bind(person)
    }

    inner class PeopleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                personClickEvent?.invoke(items[adapterPosition].id)
            }
        }

        fun bind(person: Person) {
            itemView.tvPersonName.text = person.name
            itemView.tvPersonGender.text = person.gender
            itemView.tvPersonHeight.text = person.height
            itemView.tvPersonMass.text = person.mass
        }
    }
}