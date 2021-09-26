package com.arthurgonzaga.wikistarwars.ui.adapters

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arthurgonzaga.wikistarwars.R
import com.arthurgonzaga.wikistarwars.data.model.CharacterEntity
import com.arthurgonzaga.wikistarwars.databinding.RvCharacterItemBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CharacterAdapter @Inject constructor(
    @ApplicationContext private val context: Context
) : PagingDataAdapter<CharacterEntity, CharacterAdapter.VH>(DiffUtilCallback) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = RvCharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val characterEntity = getItem(position)

        holder.title.text = characterEntity?.name
        holder.gender.text = characterEntity?.gender
        holder.subtitle.text =
            context.getString(R.string.rv_subtitle, characterEntity?.height, characterEntity?.weight)

        if (characterEntity?.isFavorite == true) {
            val drawable = ResourcesCompat.getDrawable(
                context.resources,
                R.drawable.ic_favorite,
                context.theme
            )
            holder.favoriteButton.setImageDrawable(drawable)
        }
    }

    class VH(binding: RvCharacterItemBinding) : RecyclerView.ViewHolder(binding.root){
        val title = binding.title
        val gender = binding.gender
        val subtitle = binding.subtitle
        val favoriteButton = binding.favoriteButton
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