package com.matheussilas97.starwarsapp.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matheussilas97.starwarsapp.api.response.CharactersDetailsResponse
import com.matheussilas97.starwarsapp.databinding.ItemCharactersListBinding

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var charactersList: List<CharactersDetailsResponse> = ArrayList()

    inner class MainViewHolder(val binding: ItemCharactersListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(i: CharactersDetailsResponse) {
            binding.txtName.text = i.name
            binding.txtGender.text = "Gênero: ${i.gender}"
            binding.txtHeight.text = "Altura: ${i.height}"
            binding.txtMass.text = "Peso: ${i.mass}"
            binding.layout.setOnClickListener {
                onItemClickLister?.onClick(1)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ItemCharactersListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(charactersList[position])
    }

    override fun getItemCount(): Int = charactersList.size


    fun updateTask(itemList: List<CharactersDetailsResponse>) {
        this.charactersList = itemList
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onClick(id: Int)
    }

    private var onItemClickLister: OnItemClickListener? = null

    fun addOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickLister = onItemClickListener
    }

}