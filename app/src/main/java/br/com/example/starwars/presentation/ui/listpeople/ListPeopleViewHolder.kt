package br.com.example.starwars.presentation.ui.listpeople

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.example.starwars.R
import br.com.example.starwars.databinding.ItemPeopleBinding
import br.com.example.starwars.domain.entities.People

class ListPeopleViewHolder(
    private val binding: ItemPeopleBinding,
    private val callbackClick: (People) -> Unit,
    private val callbackFavorite: (People) -> Unit
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

            if (!people.favorite) {
                favorite.setImageResource(R.drawable.ic_favorite_border)
            } else {
                favorite.setImageResource(R.drawable.ic_favorite)
            }
        }
        setupFavorite(people)
    }

    private fun setupFavorite(people: People) {
        with(binding) {
            favorite.setOnClickListener {
                if (people.favorite) {
                    favorite.setImageResource(R.drawable.ic_favorite_border)
                } else {
                    favorite.setImageResource(R.drawable.ic_favorite)
                }
                people.favorite = people.favorite.not()
                callbackFavorite.invoke(people)
            }
        }
    }

    companion object {
        fun inflate(
            parent: ViewGroup,
            callbackClick: (People) -> Unit,
            callbackFavorite: (People) -> Unit
        ): ListPeopleViewHolder {
            return ListPeopleViewHolder(
                ItemPeopleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                callbackClick,
                callbackFavorite
            )
        }
    }
}