package com.github.weslleystos.features.list.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.weslleystos.R
import com.github.weslleystos.databinding.PeoplesRowBinding
import com.github.weslleystos.shared.entities.People

class PeoplesAdapter(private val fragment: PeoplesList) :
    RecyclerView.Adapter<PeoplesAdapter.PeopleVH>() {
    private val dataset = mutableListOf<People>()
    private val peoples = mutableListOf<People>()

    fun setPeoples(peoples: List<People>) {
        val oldSize = this.dataset.size
        this.dataset.addAll(peoples)
        this.peoples.addAll(peoples)
        notifyItemRangeInserted(oldSize, this.dataset.size)
    }

    fun setFilter(word: String) {
        this.dataset.clear()
        if (word.isBlank()) {
            fragment.isSearching = false
            this.dataset.addAll(peoples)
        } else {
            fragment.isSearching = true
            val filteredPeoples = peoples.filter { people -> people.name.contains(word, true) }
            this.dataset.addAll(filteredPeoples)
        }
        notifyDataSetChanged()
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
        holder.bind(dataset[position])
    }

    override fun getItemCount() = dataset.size

    inner class PeopleVH(private val binding: PeoplesRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(people: People) {
            binding.people = people
            binding.container.setOnClickListener {
                fragment.peoplesViewModel.clearLiveData()
                fragment.findNavController().navigate(
                    R.id.action_PeopleListFragment_to_DetailFragment,
                    bundleOf("people" to people)
                )
            }
        }
    }
}