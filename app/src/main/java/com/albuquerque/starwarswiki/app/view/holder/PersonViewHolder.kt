package com.albuquerque.starwarswiki.app.view.holder

import br.albuquerque.data.ui.PersonUI
import com.albuquerque.starwarswiki.app.view.handler.PersonHandler
import br.albuquerque.core.holder.BaseViewHolder
import com.albuquerque.starwarswiki.databinding.ItemPeopleBinding

class PersonViewHolder(binding: ItemPeopleBinding): BaseViewHolder<PersonUI>(binding) {

    lateinit var personHandler: PersonHandler

    override fun bind(item: PersonUI) {
        with(binding as ItemPeopleBinding) {
            person = item
            position = adapterPosition
            handler = personHandler
        }
    }

    fun bind(item: PersonUI, handler: PersonHandler) {
        this.personHandler = handler
        bind(item)
    }

}