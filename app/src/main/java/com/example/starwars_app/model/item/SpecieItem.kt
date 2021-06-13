package com.example.starwars_app.model.item

import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import com.example.starwars_app.R
import com.example.starwars_app.model.Especie
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.row_species.view.*

class SpecieItem(private val specie: Especie): Item<ViewHolder>(){
    @SuppressLint("SetTextI18n")
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.txt_specie.text = specie.name+" - "+specie.designation+" "+ specie.classification
        viewHolder.itemView.txt_specie_info1.text = "Average "+specie.averageHeight + " height " +
                "\nAverage "+specie.averageLifespan+" years lifespan"
        viewHolder.itemView.txt_specie_info2.text = "Speaks "+specie.language
        viewHolder.itemView.txt_specie_info3.text =specie.eyeColors+" eyes\n"+specie.hairColors+" hair"

        //Se o nome for um desses descritos, coloca uma imagem individual. Se não, usa um placeholder.
        val drawable =  when(specie.name){
            "Human"-> R.drawable.humanos
            "Droid" -> R.drawable.droides
            "Yoda's Species" -> R.drawable.yodas
            "Wookiee" -> R.drawable.wookies
            "Gand" -> R.drawable.gands
            "Rodiano" -> R.drawable.rodianos
            "Trandoshans" -> R.drawable.trandoshans
            "Twileks" -> R.drawable.twileks
            else -> "https://image.freepik.com/free-icon/alien_318-1528.jpg"
        }
        Glide.with(viewHolder.itemView).load(drawable).centerCrop().into(viewHolder.itemView.mgv_specie)
    }
    override fun getLayout(): Int {
        return R.layout.row_species
    }
}