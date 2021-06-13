package com.example.starwars_app.model.item

import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import com.example.starwars_app.R
import com.example.starwars_app.model.Veiculo
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.row_vehicles.view.*

class VehicleItem(private val vehicle: Veiculo): Item<ViewHolder>(){
    @SuppressLint("SetTextI18n")
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.txt_vehicle.text = vehicle.name+"\n"+vehicle.model
        viewHolder.itemView.txt_vehicle_info1.text = vehicle.vehicleClass+" vehicle\nMade by "+ vehicle.manufacturer
        viewHolder.itemView.txt_vehicle_info5.text = "Transports "+vehicle.crew+" crew members\nand "+vehicle.passengers+" passengers"+
                "\nWith "+vehicle.consumables+" worth of supplies\nand "+vehicle.cargoCapacity+" kilos of cargo"
        viewHolder.itemView.txt_vehicle_info3.text = vehicle.length+" meters in length\nCosts "+vehicle.costInCredits+" credits"

        //Se o nome for um desses descritos, coloca uma imagem individual. Se não, usa um placeholder.
        val drawable =  when(vehicle.name){
            "Sand Crawler"-> "https://vignette.wikia.nocookie.net/starwars/images/f/ff/Sandcrawler.png/revision/latest?cb=20130812001443"
            "Zephyr-G swoop bike"-> "http://kenkoontzart.com/wp-content/uploads/2011/07/ZephyrG_01.jpg"
            "AT-AT" ->"https://vignette.wikia.nocookie.net/disney/images/0/01/AT-AT_DICE.png/revision/latest?cb=20161024082827"
            else -> "data:image/jpeg;base64,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"
        }
        Glide.with(viewHolder.itemView).load(drawable).centerCrop().into(viewHolder.itemView.mgv_circle_vehicle)
    }
    override fun getLayout(): Int {
        return R.layout.row_vehicles
    }
}