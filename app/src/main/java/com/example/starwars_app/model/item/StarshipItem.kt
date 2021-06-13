package com.example.starwars_app.model.item

import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import com.example.starwars_app.R
import com.example.starwars_app.model.Nave
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.row_starships.view.*

class StarshipItem(private val starship: Nave): Item<ViewHolder>(){
    @SuppressLint("SetTextI18n")
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.txt_starship.text = starship.name+"\n"+starship.model
        viewHolder.itemView.txt_starship_info1.text = starship.starshipClass+"\n\nMade by "+ starship.manufacturer
        viewHolder.itemView.txt_vehicle_info5.text = "Transports "+starship.crew+" crew members\nAnd "+starship.passengers+" passengers"+
                "\nWith "+starship.consumables+" worth of supplies"
        viewHolder.itemView.txt_vehicle_info3.text = starship.length+" meters in length\nCosts "+starship.costInCredits+" credits"
        viewHolder.itemView.txt_starship_info4.text ="Speed: "+starship.mglt+" per hour \nHyperdrive Rating: "+
                starship.hyperdriveRating+"\nMax Atmosphering Speed: "+starship.maxAtmospheringSpeed

        //Se o nome for um desses descritos, coloca uma imagem individual. Se não, usa um placeholder.
        val drawable =  when(starship.name){
            "CR90 corvette"-> "http://wiki.rebelsquadrons.org/images/5/5d/CR90_Corellian_corvette.jpg"
            "Death Star" -> "https://lumiere-a.akamaihd.net/v1/images/Death-Star-I-copy_36ad2500.jpeg?region=0%2C0%2C1600%2C900&width=960"
            "V-wing" -> "https://img2.cgtrader.com/items/794112/d88cb7965f/large/star-wars-ep3-v-wing-fighter-3d-model-obj-mtl-fbx-lwo-lw-lws-stl.jpg"
            "Millennium Falcon" -> "https://www.gundamplanet.com/pub/media/catalog/product/cache/aa72b28f82ebf2d897600ee194018ec6/p/g/pg-1-72-millennium-falcon-00.jpg"
            "X-wing" -> "https://www.hallmark.com/dw/image/v2/AALB_PRD/on/demandware.static/-/Sites-hallmark-master/default/dw4306628d/images/finished-goods/Star-Wars-XWing-Starfighter-Ornament-With-Light-and-Sound_3999QXI3473_01.jpg?sw=1024"
            "TIE Advanced x1" -> "https://vignette.wikia.nocookie.net/starwars/images/1/1d/Vader_TIEAdvanced_SWB.png/revision/latest?cb=20160915042032"
            else -> "https://cdn3.iconfinder.com/data/icons/startup-soft/512/rocket_science_shuttle_space_ship_spaceship_technology_space_shuttle_star_trek-512.png"
        }
        Glide.with(viewHolder.itemView).load(drawable).centerCrop().into(viewHolder.itemView.mgv_circle_starship)
    }
    override fun getLayout(): Int {
        return R.layout.row_starships
    }
}