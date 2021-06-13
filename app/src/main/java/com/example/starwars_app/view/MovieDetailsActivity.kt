package com.example.starwars_app.view

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.example.starwars_app.R
import com.example.starwars_app.model.Filme
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
            val movieIntent = intent.getStringExtra("movie")
            val movie = Gson().fromJson<Filme>(movieIntent,Filme::class.java)

          /*  var jediFont = Typeface.createFromAsset(assets, "starjedi.ttf")
            textViewOpeningCrawl.typeface = jediFont
            txt_namedirector.typeface = jediFont
            textViewProducer.typeface = jediFont
            releaseDate.typeface = jediFont
            episodeId.typeface = jediFont*/


           // txt_film_starship.text = movie.title
            openingCrawl.text = movie.openingCrawl
            txt_namedirector.text = "Directed by "+movie.director
            textViewProducer.text = "Produced by "+movie.producer
            episodeId.text = movie.episodeId.toString()+"° star wars movie chronologically"


            val date = String.format("Release date: %02d/%02d/%d",
                movie.releaseDate.day,movie.releaseDate.month,movie.releaseDate.year+1900)
                    releaseDate.text = date

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
        Glide.with(this).load(drawable).centerCrop().into(mgv_posterfilm)
            backButton.setOnClickListener {
                finish()
            }
        buttonCharacters.setOnClickListener {
            val i = Intent(this, CharactersActivity::class.java)
            i.putStringArrayListExtra("charactersUrls", movie.characters)
            i.putExtra("movieTitle",movie.title)
                startActivity(i)
        }
        buttonSpecies.setOnClickListener {
            val i = Intent(this, SpeciesActivity::class.java)
            i.putStringArrayListExtra("speciesUrls", movie.species)
            i.putExtra("movieTitle",movie.title)
                startActivity(i)
        }
        buttonPlanets.setOnClickListener {
            val i = Intent(this, PlanetsActivity::class.java)
            i.putStringArrayListExtra("planetsUrls", movie.planets)
            i.putExtra("movieTitle",movie.title)
                startActivity(i)
        }
        buttonStarships.setOnClickListener {
            val i = Intent(this, StarshipsActivity::class.java)
            i.putStringArrayListExtra("starshipsUrls", movie.starships)
            i.putExtra("movieTitle",movie.title)
                startActivity(i)
        }
        buttonVehicles.setOnClickListener {
            val i = Intent(this, VehiclesActivity::class.java)
            i.putStringArrayListExtra("vehiclesUrls", movie.vehicles)
            i.putExtra("movieTitle",movie.title)
                startActivity(i)
        }

    }
}
