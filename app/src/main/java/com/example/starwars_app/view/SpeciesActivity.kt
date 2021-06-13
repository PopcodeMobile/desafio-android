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
import com.example.starwars_app.model.item.SpecieItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_species.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class SpeciesActivity : AppCompatActivity() {
//    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_species)

        val speciesUrls = intent.getStringArrayListExtra("speciesUrls")
        val movieName = intent.getStringExtra("movieTitle")
        val list = ArrayList<SpecieItem>()
        val api = StarWarsApi()
        val specieAdapter = GroupAdapter<ViewHolder>()

        /*val jediFont = Typeface.createFromAsset(assets, "fonts/Starjedi.ttf")
        txt_film_starship.typeface = jediFont*/
        txt_film_starship.text = "$movieName's Species"


        listViewSpecies.layoutManager = LinearLayoutManager(this)
        listViewSpecies.adapter = specieAdapter
        listViewSpecies.isNestedScrollingEnabled = false
        listViewSpecies.isFocusable = false

        /*       loadingDialog = LoadingDialog(this)
               loadingDialog.showDialog()*/

        if (speciesUrls != null) {
            api.loadSpecies(speciesUrls)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( {specie ->
                    /* if(loadingDialog.isShowing()== true)
                         loadingDialog.hideDialog()*/

                    list.add(SpecieItem(specie))
                    specieAdapter.clear()
                    specieAdapter.addAll(list)
                    specieAdapter.notifyDataSetChanged()
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
