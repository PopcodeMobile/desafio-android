package com.arthurgonzaga.wikistarwars.ui.adapters

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.res.ResourcesCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arthurgonzaga.wikistarwars.R
import com.arthurgonzaga.wikistarwars.data.model.CharacterEntity
import com.arthurgonzaga.wikistarwars.databinding.RvCharacterItemBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CharacterAdapter(
    private val context: Context,
    private val navigateToDetail: (character: CharacterEntity) -> Unit,
    private val favoriteCharacter: (characterId: Int, isFavorite: Boolean) -> Unit
) : PagingDataAdapter<CharacterEntity, CharacterAdapter.VH>(DiffUtilCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding =
            RvCharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val currentItem = getItem(position)

        currentItem?.let { character ->
            holder.title.text = character.name
            holder.gender.text = character.gender
            holder.subtitle.text =
                context.getString(R.string.rv_subtitle, character.height, character.weight)
            holder.favoriteButton.setImage(character.isFavorite)
            holder.root.setOnClickListener { _ ->
                navigateToDetail(character)
            }
            holder.favoriteButton.setOnClickListener { _ ->
                favoriteCharacter(character.id, !character.isFavorite)
            }
        }
    }

    private fun ImageButton.setImage(isFavorite: Boolean) {
        val drawable = ResourcesCompat.getDrawable(
            context.resources,
            if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border,
            context.theme
        )
        this.setImageDrawable(drawable)
    }

    class VH(binding: RvCharacterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.title
        val gender = binding.gender
        val subtitle = binding.subtitle
        val favoriteButton = binding.favoriteButton
        val root = binding.root
    }


    object DiffUtilCallback : DiffUtil.ItemCallback<CharacterEntity>() {
        override fun areItemsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CharacterEntity,
            newItem: CharacterEntity
        ): Boolean {
            return oldItem == newItem
        }
    }
}