package com.matheussilas97.starwarsapp.view.charactersdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matheussilas97.starwarsapp.databinding.ItemSpeciesBinding

class SpeciesAdapter: RecyclerView.Adapter<SpeciesAdapter.SpeciesViewHolder>() {

    private var specieList: List<String> = ArrayList()

    inner class SpeciesViewHolder(val binding: ItemSpeciesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(i: String) {
            binding.txtSpecies.text = i
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpeciesViewHolder {
        val binding =
            ItemSpeciesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpeciesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpeciesViewHolder, position: Int) {
        holder.bind(specieList[position])
    }

    override fun getItemCount(): Int = specieList.size


    fun updateTask(itemList: List<String>) {
        this.specieList = itemList
        notifyDataSetChanged()
    }


}