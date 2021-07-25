package com.example.desafio_android.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio_android.R
import com.example.desafio_android.data.model.People
import com.example.desafio_android.databinding.RowPeopleBinding

class PeopleAdapter : PagingDataAdapter<People, PeopleAdapter.MyViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowPeopleBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class MyViewHolder(private val binding: RowPeopleBinding) :
    RecyclerView.ViewHolder(binding.root) {

        fun bind(people: People) {
            binding.apply {
                nomePersonagem.text = people.name
                alturaPersonagem.text = people.height +" " +
                        alturaPersonagem.context.getString(R.string.cm)
                generoPersonagem.text = people.gender
                pesoPersonagem.text = people.mass + " " +
                        pesoPersonagem.context.getString(R.string.kg)
            }
        }

    }

    companion object{
        private val COMPARATOR = object : DiffUtil.ItemCallback<People>() {
            override fun areItemsTheSame(oldItem: People, newItem: People): Boolean =
                oldItem.name == newItem.created

            override fun areContentsTheSame(oldItem: People, newItem: People): Boolean =
                oldItem == newItem
        }
    }
}