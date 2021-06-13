package com.example.starwars_app.model.item

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.Window
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.starwars_app.R
import com.example.starwars_app.model.Filme
import com.example.starwars_app.view.MovieDetailActivity
import com.google.gson.Gson
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.row_movies.view.*

class MovieItem(private val context: Context, private val movie: Filme): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.txt_film_starship.text = movie.title
        val yearRelease = 1900+ movie.releaseDate.year
        viewHolder.itemView.txt_dateview.text = "Released in $yearRelease"
        viewHolder.itemView.txt_namedirector.text = "Directed by "+movie.director
        viewHolder.itemView.setOnClickListener {
            val i = Intent(context, MovieDetailActivity::class.java)
            i.putExtra("movie", Gson().toJson(movie))
            context.startActivity(i)
        }

        viewHolder.itemView.btn_sinopse.setOnClickListener {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCanceledOnTouchOutside(true)
            val view = LayoutInflater.from(context).inflate(R.layout.popup_opening,null)
            val textViewOpening = view.findViewById<TextView>(R.id.txt_description)
            val textViewOpeningTitle = view.findViewById<TextView>(R.id.txt_title)
            textViewOpening.text = movie.openingCrawl
            textViewOpeningTitle.text = movie.title
            dialog.setContentView(view)
            dialog.show()
        }
        val drawable =  when(movie.episodeId){
            1-> R.drawable.filme1
            2 -> R.drawable.filme2
            3 -> R.drawable.filme3
            4 -> R.drawable.filme4
            5 -> R.drawable.filme5
            6 -> R.drawable.filme6
            7 -> R.drawable.filme7
            else -> R.drawable.imagedefault
        }
        Glide.with(viewHolder.itemView).load(drawable).centerCrop().into(viewHolder.itemView.mgv_posterfilm)
    }
    override fun getLayout(): Int {
        return R.layout.row_movies
    }
}