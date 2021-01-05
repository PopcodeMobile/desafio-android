package com.github.weslleystos.features.list.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.weslleystos.databinding.PeoplesRowBinding
import com.github.weslleystos.shared.entities.People

class PeoplesAdapter : RecyclerView.Adapter<PeoplesAdapter.PeopleVH>() {
    private val peoples = mutableListOf<People>()

    fun setPeoples(peoples: List<People>) {
        val oldSize = this.peoples.size
        this.peoples.addAll(peoples)
        notifyItemRangeInserted(oldSize, this.peoples.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleVH {
        return PeopleVH(
            PeoplesRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PeopleVH, position: Int) {
        holder.bind(peoples[position])
    }

    override fun getItemCount() = peoples.size

    inner class PeopleVH(private val binding: PeoplesRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(people: People) {
            binding.people = people
        }
    }
}