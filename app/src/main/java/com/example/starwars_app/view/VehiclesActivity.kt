package com.example.starwars_app.view

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.starwars_app.R
import com.example.starwars_app.model.api.StarWarsApi
import com.example.starwars_app.model.item.VehicleItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_vehicles.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class VehiclesActivity : AppCompatActivity() {
//    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicles)

        val vehiclesUrls = intent.getStringArrayListExtra("vehiclesUrls")
        val movieName = intent.getStringExtra("movieTitle")
        val list = ArrayList<VehicleItem>()
        val api = StarWarsApi()
        val vehicleAdapter = GroupAdapter<ViewHolder>()

        /*val jediFont = Typeface.createFromAsset(assets, "fonts/Starjedi.ttf")
        txt_vehicle_filme.typeface = jediFont*/
        txt_vehicle_filme.text = "$movieName's vehicles"

        rcv_vehicle.layoutManager = LinearLayoutManager(this)
        rcv_vehicle.adapter = vehicleAdapter
        rcv_vehicle.isNestedScrollingEnabled = false
        rcv_vehicle.isFocusable = false
        /*      loadingDialog = LoadingDialog(this)
              loadingDialog.showDialog()*/
        if (vehiclesUrls != null) {
            api.loadVehicles(vehiclesUrls)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( {vehicle ->
                    /* if(loadingDialog.isShowing()== true)
                         loadingDialog.hideDialog()*/
                    list.add(VehicleItem(vehicle))
                    vehicleAdapter.clear()
                    vehicleAdapter.addAll(list)
                    vehicleAdapter.notifyDataSetChanged()
                },{
                    e -> e.printStackTrace()
                },{

                })
        }
        Glide.with(this).load(R.drawable.background).centerCrop()
            .into(object : SimpleTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    mainLayout.background = resource
                }
            })
    }
}