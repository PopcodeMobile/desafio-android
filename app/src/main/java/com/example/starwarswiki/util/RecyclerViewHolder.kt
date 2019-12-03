package com.example.starwarswiki.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarswiki.databinding.ItemListFragmentBinding
import com.example.starwarswiki.domain.PersonModel
import com.example.starwarswiki.viewmodel.FavoriteClickListener
import com.example.starwarswiki.viewmodel.PersonClickListener

class  RecyclerViewHolder private constructor(private val binding: ItemListFragmentBinding)
    : RecyclerView.ViewHolder(binding.root){
    companion object{
        fun from(parent: ViewGroup): RecyclerViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemListFragmentBinding.inflate(layoutInflater, parent, false)
            return RecyclerViewHolder(binding)
        }
    }
    fun bind(
        item: PersonModel,
        index: Int,
        clickListener: PersonClickListener,
        favoriteClickListener: FavoriteClickListener
    ){
        binding.favoriteClickListener = favoriteClickListener
        binding.position = index
        binding.clickListener = clickListener
        binding.person = item
        binding.executePendingBindings()
    }
}