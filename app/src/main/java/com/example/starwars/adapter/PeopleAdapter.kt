package com.example.starwars.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwars.R
import com.example.starwars.model.People
import com.example.starwars.model.Results
import kotlinx.android.synthetic.main.custom_row_people.view.*

class PeopleAdapter: RecyclerView.Adapter<PeopleAdapter.MyViewHolder>() {

    // Declara uma Lista vazia
    private var myListResults = emptyList<People>()

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row_people, parent, false))
    }

    override fun getItemCount(): Int {
        return myListResults.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = myListResults[position]
        holder.itemView.textName.text = currentItem.results[position].name
        holder.itemView.textHeigth.text = currentItem.results[position].heigth
        holder.itemView.textGender.text = currentItem.results[position].gender
        holder.itemView.textMass.text = currentItem.results[position].mass
    }

    fun setData(newList: List<People>){
        this.myListResults = newList
        notifyDataSetChanged()
    }

}