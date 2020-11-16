package br.com.example.starwars.presentation.ui.listpeople

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.example.starwars.databinding.ItemPeopleBinding
import br.com.example.starwars.domain.entities.People

class ListPeopleViewHolder(
    private val binding: ItemPeopleBinding,
    private val callbackClick: (People) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(people: People) {
        with(binding) {
            name.text = people.name
            height.text = people.height
            mass.text = people.mass
            gender.text = people.gender
            cardItem.setOnClickListener {
                callbackClick.invoke(people)
            }
        }
    }

    companion object {
        fun inflate(parent: ViewGroup, callbackClick: (People) -> Unit): ListPeopleViewHolder {
            return ListPeopleViewHolder(
                ItemPeopleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                callbackClick
            )
        }
    }
}