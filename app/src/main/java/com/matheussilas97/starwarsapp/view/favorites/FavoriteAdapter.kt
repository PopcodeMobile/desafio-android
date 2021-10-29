package com.matheussilas97.starwarsapp.view.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matheussilas97.starwarsapp.database.model.FavoriteModel
import com.matheussilas97.starwarsapp.databinding.ItemFavoriteBinding

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var charactersList: MutableList<FavoriteModel> = mutableListOf()

    inner class FavoriteViewHolder(val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(i: FavoriteModel) {
            binding.txtName.text = i.idName
            binding.delete.setOnClickListener {
                onItemClickLister?.onDelete(i.idName)
            }
            binding.layout.setOnClickListener {
                onItemClickLister?.onClick(i.url)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(charactersList[position])
    }

    override fun getItemCount(): Int = charactersList.size


    fun updateList(itemList: List<FavoriteModel>) {
        charactersList.addAll(itemList)
        notifyDataSetChanged()
    }


    fun clear() {
        charactersList.clear()
        // notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onClick(url: String)
        fun onDelete(id: String)
    }

    private var onItemClickLister: OnItemClickListener? = null

    fun addOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickLister = onItemClickListener
    }

}