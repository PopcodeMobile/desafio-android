package com.knowledge.wikisw_luan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.knowledge.wikisw_luan.R
import com.knowledge.wikisw_luan.models.Character

class Adapter(private val characters: List<Character>): RecyclerView.Adapter<Adapter.CharViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.char_item, parent, false)
        return CharViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    class CharViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: Character) {
            with(itemView) {
                val swName = findViewById<TextView>(R.id.sw_name)
                val swHeight = findViewById<TextView>(R.id.sw_height)
                val swGender = findViewById<TextView>(R.id.sw_gender)
                val swMass = findViewById<TextView>(R.id.sw_mass)

                swName.text = data.name
                swHeight.text = data.height
                swGender.text = data.gender
                swMass.text = data.mass
            }
        }
    }

}