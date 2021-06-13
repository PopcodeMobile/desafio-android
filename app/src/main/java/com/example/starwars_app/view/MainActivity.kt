package com.example.starwars_app.view

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.starwars_app.LoadingDialog
import com.example.starwars_app.R
import com.example.starwars_app.model.api.StarWarsApi
import com.example.starwars_app.model.item.MovieItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class MainActivity : AppCompatActivity() {
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(findViewById(R.id.toolbar))


        val movieAdapter = GroupAdapter<ViewHolder>()
        listMovies.adapter = movieAdapter
        listMovies.layoutManager = LinearLayoutManager(this)
        listMovies.isNestedScrollingEnabled = true
        listMovies.isFocusable = true

        Glide.with(this).load(R.drawable.background).centerCrop()
            .into(object : SimpleTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    mainLayout.background = resource
                }
            })
        Glide.with(this).load(R.drawable.imagedefault).into(imageViewLogo)
        loadingDialog = LoadingDialog(this)
        loadingDialog.showDialog()

        val api = StarWarsApi()
        api.loadMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {movie ->
                if(loadingDialog.isShowing()== true)
                    loadingDialog.hideDialog()
               movieAdapter.add(MovieItem(this, movie))
            },{
               e -> e.printStackTrace()
            },{
               movieAdapter.notifyDataSetChanged()

               /* Glide.with(this).load(R.drawable.background).into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        mainLayout.background = resource

                    }
                })*/
            })
    }
}
