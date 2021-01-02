package com.example.starwars.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwars.R
import com.example.starwars.model.People
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
        val currentItem = myListResults
        holder.itemView.textName.text = currentItem[position].next.toString()
       /* holder.itemView.textName.text = currentItem.name
        holder.itemView.textHeigth.text = currentItem.heigth
        holder.itemView.textGender.text = currentItem.gender
        holder.itemView.textMass.text = currentItem.mass*/
    }

    fun setData(newList: List<People>){
        this.myListResults = newList
        notifyDataSetChanged()
    }

}