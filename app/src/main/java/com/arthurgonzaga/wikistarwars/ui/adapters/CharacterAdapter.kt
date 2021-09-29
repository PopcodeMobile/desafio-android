package com.arthurgonzaga.wikistarwars.ui.adapters

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arthurgonzaga.wikistarwars.R
import com.arthurgonzaga.wikistarwars.data.model.CharacterEntity
import com.arthurgonzaga.wikistarwars.databinding.RvCharacterItemBinding
import com.arthurgonzaga.wikistarwars.util.setImage
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CharacterAdapter(
    private val context: Context,
    private val navigateToDetail: (CharacterEntity, TextView, ImageButton, ViewGroup) -> Unit,
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
            holder.binding.gender.text = character.gender
            holder.binding.subtitle.text = context.getString(R.string.rv_subtitle, character.height, character.weight)
            holder.binding.title.apply {
                text = character.name
                transitionName = "heading_small$position";
            }
            holder.binding.item.transitionName = "rv_background$position"
            holder.binding.root.apply {
                transitionName = "rv_background$position"
                setOnClickListener { _ ->
                    navigateToDetail(
                        character,
                        holder.binding.title,
                        holder.binding.favoriteButton,
                        holder.binding.item
                    )
                }
            }

            holder.binding.favoriteButton.apply {
                setImage(character.isFavorite)
                transitionName = "rv_favorite_btn$position"
                setOnClickListener { _ ->
                    favoriteCharacter(character.id, !character.isFavorite)
                }

                setOnTouchListener { view, motionEvent ->
                    when (motionEvent.action) {
                        // User touched
                        MotionEvent.ACTION_DOWN -> {
                            setImage(!character.isFavorite)
                        }
                        // User has canceled the touch
                        MotionEvent.ACTION_CANCEL -> {
                            setImage(character.isFavorite)
                        }
                        // User has canceled release the touch
                        MotionEvent.ACTION_UP -> {
                            performClick() // this calls setOnClickListener
                        }
                    }
                    true
                }
            }
        }
    }


    class VH(val binding: RvCharacterItemBinding) : RecyclerView.ViewHolder(binding.root)


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