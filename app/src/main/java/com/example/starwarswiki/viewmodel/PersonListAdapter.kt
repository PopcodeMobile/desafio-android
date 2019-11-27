package com.example.starwarswiki.viewmodel

import android.view.ViewGroup
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.starwarswiki.domain.PersonModel
import com.example.starwarswiki.util.RecyclerViewHolder
import java.util.logging.Filter
import java.util.logging.LogRecord

class PersonListAdapter(val clickListener: PersonClickListener): ListAdapter<PersonModel, RecyclerViewHolder> (PersonListDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item!!, clickListener)
    }
}

class PersonListDiffCallback: DiffUtil.ItemCallback<PersonModel>(){
    override fun areItemsTheSame(oldItem: PersonModel, newItem: PersonModel): Boolean {
        return (oldItem.url == newItem.url)
    }

    override fun areContentsTheSame(oldItem: PersonModel, newItem: PersonModel): Boolean {
        return (oldItem == newItem)
    }
}

class PersonClickListener(val clickListener: (url: String)->Unit){
    fun onClick(person: PersonModel) = clickListener(person.url)
}