package com.example.starwars_app.model.item

import com.bumptech.glide.Glide
import com.example.starwars_app.R
import com.example.starwars_app.model.Planeta
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.row_planets.view.*

class PlanetItem(private val planet: Planeta): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.txt_planet.text = planet.name+" - "+planet.population+" habitants"
        viewHolder.itemView.txt_planet_info1.text = planet.climate + " climate \n"+planet.terrain+" terrain"
        viewHolder.itemView.txt_planet_info2.text = planet.rotationPeriod + " hours day, "+planet.orbitalPeriod+" days year"
        viewHolder.itemView.txt_planet_info3.text =planet.surfaceWater+"% water. Gravity: "+planet.gravity

        val drawable =  when(planet.name){
            "Tattoine"-> R.drawable.tattoine
            "Alderaan" -> R.drawable.alderaan
            "Hoth" -> R.drawable.hoth
            "Bespin" -> R.drawable.bespin
            "Yavin" -> R.drawable.iavin4
            "Naboo" -> R.drawable.naboo
            "Cantonica" -> R.drawable.cantonica
            "Coruscant" -> R.drawable.coruscant
            "Eadu" -> R.drawable.eadu
            "Felucia" -> R.drawable.felucia
            "Jedha" -> R.drawable.jedha
            "Kamino" -> R.drawable.kamino
            "Scarif" -> R.drawable.scarif
            "Starkiller" -> R.drawable.starkillerbase
            "Takodana" -> R.drawable.takodana
            else -> R.drawable.imagedefault
        }
        Glide.with(viewHolder.itemView).load(drawable).centerCrop().into(viewHolder.itemView.mgv_planet)
    }
    override fun getLayout(): Int {
        return R.layout.row_planets
    }
}