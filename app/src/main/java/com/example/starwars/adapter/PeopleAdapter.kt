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
    private var myListPeople = emptyList<People>()

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row_people, parent, false))
    }

    override fun getItemCount(): Int {
        return myListPeople.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = myListPeople[position]
        holder.itemView.textName.text = currentItem.name.toString()
        holder.itemView.textHeigth.text = currentItem.heigth.toString()
        holder.itemView.textGender.text = currentItem.gender.toString()
        holder.itemView.textMass.text = currentItem.mass.toString()
    }

    // Captura tudo da Lista e joga para o myList
    fun setData(newList: List<People>){
        myListPeople = newList
        notifyDataSetChanged()
    }

}