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
import com.example.starwars_app.model.item.StarshipItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_starships.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class StarshipsActivity : AppCompatActivity() {
//    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starships)

        val starshipsUrls = intent.getStringArrayListExtra("starshipsUrls")
        val movieName = intent.getStringExtra("movieTitle")
        val list = ArrayList<StarshipItem>()
        val api = StarWarsApi()
        val starshipAdapter = GroupAdapter<ViewHolder>()

       /* val jediFont = Typeface.createFromAsset(assets, "fonts/Starjedi.ttf")
        txt_film_starship.typeface = jediFont*/
        txt_film_starship.text = "$movieName's Starships"

        rcv_starship.layoutManager = LinearLayoutManager(this)
        rcv_starship.adapter = starshipAdapter
        rcv_starship.isNestedScrollingEnabled = false
        rcv_starship.isFocusable = false
//        loadingDialog = LoadingDialog(this)
//        loadingDialog.showDialog()

        if (starshipsUrls != null) {
            api.loadStarships(starshipsUrls)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( {starship ->
                   /*if(loadingDialog.isShowing()== true)
                        loadingDialog.hideDialog()*/
                    list.add(StarshipItem(starship))
                    starshipAdapter.clear()
                    starshipAdapter.addAll(list)
                    starshipAdapter.notifyDataSetChanged()
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