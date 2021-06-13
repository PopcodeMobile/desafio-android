package com.example.starwars_app.view

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.starwars_app.R
import com.example.starwars_app.model.api.StarWarsApi
import com.example.starwars_app.model.item.PlanetItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_planets.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class PlanetsActivity : AppCompatActivity() {
    //private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planets)

        val planetsUrls = intent.getStringArrayListExtra("planetsUrls")
        val movieName = intent.getStringExtra("movieTitle")
        val list = ArrayList<PlanetItem>()
        val api = StarWarsApi()
        var planetAdapter = GroupAdapter<ViewHolder>()

        /*val jediFont = Typeface.createFromAsset(assets, "fonts/Starjedi.ttf")
        txt_film_starship.typeface = jediFont*/
        txt_film_starship.text = "$movieName's Planets"

        listViewPlanets.layoutManager = LinearLayoutManager(this)
        listViewPlanets.adapter = planetAdapter
        listViewPlanets.isNestedScrollingEnabled = false
        listViewPlanets.isFocusable = false

        /*loadingDialog = LoadingDialog(this)
         loadingDialog.showDialog()*/

        if (planetsUrls != null) {
            api.loadPlanets(planetsUrls)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( { planet ->
                   /*if(loadingDialog.isShowing()== true)
                        loadingDialog.hideDialog()*/
                    list.add(PlanetItem(planet))
                    planetAdapter.clear()
                    planetAdapter.addAll(list)
                    planetAdapter.notifyDataSetChanged()
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
