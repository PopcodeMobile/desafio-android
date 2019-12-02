package com.example.starwarswiki.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarswiki.R
import com.example.starwarswiki.databinding.ItemListFragmentBinding
import com.example.starwarswiki.domain.PersonModel
import com.example.starwarswiki.generated.callback.OnClickListener
import com.example.starwarswiki.viewmodel.FavoriteClickListener
import com.example.starwarswiki.viewmodel.PersonClickListener
import kotlinx.android.synthetic.main.item_list_fragment.view.*
import timber.log.Timber
import kotlin.coroutines.coroutineContext

class  RecyclerViewHolder private constructor(val binding: ItemListFragmentBinding)
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