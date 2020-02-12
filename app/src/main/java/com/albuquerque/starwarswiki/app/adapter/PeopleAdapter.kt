package com.albuquerque.starwarswiki.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.albuquerque.starwarswiki.R
import br.albuquerque.data.ui.PersonUI
import com.albuquerque.starwarswiki.app.view.handler.PersonHandler
import com.albuquerque.starwarswiki.app.view.holder.PersonViewHolder
import br.albuquerque.core.adapter.BaseAdapter
import com.albuquerque.starwarswiki.databinding.ItemPeopleBinding
import com.albuquerque.starwarswiki.databinding.ItemPeopleBindingImpl

class PeopleAdapter(val handler: PersonHandler): BaseAdapter<PersonUI, PersonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_people, parent, false)
        return PersonViewHolder(ItemPeopleBindingImpl(null,view) as ItemPeopleBinding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(items[position], handler)
    }

}