package com.example.starwarswiki.viewmodel

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.starwarswiki.domain.PersonModel
import com.example.starwarswiki.util.RecyclerViewHolder
import timber.log.Timber

class PersonListAdapter(
    val clickListener: PersonClickListener,
    val favoriteClickListener: FavoriteClickListener
): ListAdapter<PersonModel, RecyclerViewHolder> (PersonListDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item!!, position, clickListener, favoriteClickListener)
    }
}

class PersonListDiffCallback: DiffUtil.ItemCallback<PersonModel>(){
    override fun areItemsTheSame(oldItem: PersonModel, newItem: PersonModel): Boolean {
        return (oldItem.id == newItem.id)
    }

    override fun areContentsTheSame(oldItem: PersonModel, newItem: PersonModel): Boolean {
        return (oldItem == newItem)
    }
}

class PersonClickListener(val clickListener: (id: Int)->Unit){
    fun onClick(person: PersonModel){
        return clickListener(person.id)
    }
}

class FavoriteClickListener(val clickListener: (id: PersonModel, position: Int) -> Unit){
    fun onClick(person: PersonModel, position: Int){
        return clickListener(person, position)
    }
}