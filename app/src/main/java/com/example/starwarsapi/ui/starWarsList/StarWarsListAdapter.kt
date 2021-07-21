package com.example.starwarsapi.ui.starWarsList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsapi.model.api.StarWarsResult
import com.example.starwarsapi.R
import kotlinx.android.synthetic.main.card_starwars_search.view.*

class StarWarsListAdapter(val starWarsClick: (Int) -> Unit ):RecyclerView.Adapter<StarWarsListAdapter.SearchViewHolder>() {
    var starWarsList : List <StarWarsResult> = emptyList<StarWarsResult>()

    fun setData(list : List<StarWarsResult>){
        starWarsList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_starwars_search,parent,false))
    }

    override fun getItemCount(): Int {
        return starWarsList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val starWars = starWarsList [position]
        holder.itemView.text_name_person.text=starWars.name
        holder.itemView.text_gender_person.text="Genero: ${starWars.gender}"
        holder.itemView.text_mass_person.text="Peso: ${starWars.mass}kg"
        holder.itemView.text_height_person.text="Altura: ${starWars.heigth}cm"

        holder.itemView.setOnClickListener{starWarsClick(position+1)}
    }

    class SearchViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
}