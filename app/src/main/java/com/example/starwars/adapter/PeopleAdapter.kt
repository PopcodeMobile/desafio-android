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
    private var myListResults = emptyList<Results>()

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row_people, parent, false))
    }

    override fun getItemCount(): Int {
        return myListResults.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = myListResults[position]
        holder.itemView.textName.text = currentItem.name
        holder.itemView.textHeight.text = currentItem.height
        holder.itemView.textGender.text = currentItem.gender
        holder.itemView.textMass.text = currentItem.mass
    }

    fun setData(newList: List<Results>){
        this.myListResults = newList
        notifyDataSetChanged()
    }

}