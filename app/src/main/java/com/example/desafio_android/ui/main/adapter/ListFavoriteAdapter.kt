package com.example.desafio_android.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio_android.R
import com.example.desafio_android.data.model.People
import com.example.desafio_android.databinding.RowPeopleBinding
import com.example.desafio_android.ui.main.view.ListFavoriteDirections

class ListFavoriteAdapter :
    ListAdapter<People, ListFavoriteAdapter.ListViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            RowPeopleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListFavoriteAdapter.ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ListViewHolder(private val bindind: RowPeopleBinding) :
        RecyclerView.ViewHolder(bindind.root) {

        fun bind(people: People) {
            bindind.apply {
                nomePersonagem.text = people.name
                alturaPersonagem.text = people.height + " " +
                        alturaPersonagem.context.getString(R.string.cm)
                generoPersonagem.text = people.gender
                pesoPersonagem.text = people.mass + " " +
                        pesoPersonagem.context.getString(R.string.kg)

                rowPeople.setOnClickListener {
                    val action = ListFavoriteDirections.actionListFavoriteToDetailsFragment(people)
                    rowPeople.findNavController().navigate(action)
                }


            }
        }
    }
}

private object DiffUtilCallback : DiffUtil.ItemCallback<People>() {
    override fun areItemsTheSame(oldItem: People, newItem: People): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: People, newItem: People): Boolean =
        oldItem == newItem
}