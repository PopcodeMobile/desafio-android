package com.example.starwarswiki.viewmodel

import android.app.Person
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarswiki.R
import com.example.starwarswiki.databinding.ItemListFragmentBinding
import com.example.starwarswiki.domain.PersonModel
import com.example.starwarswiki.util.RecyclerViewHolder
import org.w3c.dom.Text

class PersonListAdapter: ListAdapter<PersonModel, RecyclerViewHolder>(PersonListDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
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