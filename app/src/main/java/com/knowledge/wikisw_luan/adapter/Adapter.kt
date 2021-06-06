package com.knowledge.wikisw_luan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.knowledge.wikisw_luan.R
import com.knowledge.wikisw_luan.models.Character

class Adapter(
    private val listener: ClickWikiListener
) : RecyclerView.Adapter<Adapter.CharViewHolder>() {

    private var characters: List<Character> = listOf()

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

    fun updateList(list: List<Character>) {
        characters = list
        notifyDataSetChanged()
    }

    inner class CharViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val swName = itemView.findViewById<TextView>(R.id.sw_name)
        private val swHeight = itemView.findViewById<TextView>(R.id.sw_height)
        private val swGender = itemView.findViewById<TextView>(R.id.sw_gender)
        private val swMass = itemView.findViewById<TextView>(R.id.sw_mass)
        private val swConstraint = itemView.findViewById<ConstraintLayout>(R.id.sw_container)
        private val swFav = itemView.findViewById<ImageView>(R.id.sw_fav)

        fun bind(data: Character) {
            with(itemView) {
                swConstraint.setOnClickListener {
                    listener.onListClick(data)
                }

                swFav.setOnClickListener {
                    listener.onFavClick(data)
                }

                swName.text = data.name
                swHeight.text = "Altura: ${data.height}"
                swGender.text = "Gênero: ${data.gender}"
                swMass.text = "Peso: ${data.mass}"
            }
        }
    }

}