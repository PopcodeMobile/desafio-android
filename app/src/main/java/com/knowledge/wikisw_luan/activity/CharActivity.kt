package com.knowledge.wikisw_luan.activity

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.knowledge.wikisw_luan.R
import com.knowledge.wikisw_luan.models.CharacterModel

class CharActivity : AppCompatActivity() {

    lateinit var swName : TextView
    lateinit var swHeight : TextView
    lateinit var swGender: TextView
    lateinit var swWeight: TextView
    lateinit var swHair: TextView
    lateinit var swSkin: TextView
    lateinit var swEye: TextView
    lateinit var swBirth: TextView
    lateinit var swHome: TextView
    lateinit var swSpecie: TextView
    lateinit var swFav: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.char_info)
        swName = findViewById(R.id.sw_name)
        swHeight = findViewById(R.id.sw_height)
        swGender = findViewById(R.id.sw_gender)
        swWeight = findViewById(R.id.sw_mass)
        swHair = findViewById(R.id.sw_hair)
        swSkin = findViewById(R.id.sw_skin)
        swEye = findViewById(R.id.sw_eye)
        swBirth = findViewById(R.id.sw_bday)
        swHome = findViewById(R.id.sw_home)
        swSpecie = findViewById(R.id.sw_specie)
        swFav = findViewById(R.id.sw_favorite)

        val charInfo = intent.getParcelableExtra("CharInfo") as? CharacterModel
        charInfo?.let {
            swName.text = it.name
            swHeight.text = "Altura: ${it.height}"
            swGender.text = "Gênero: ${it.gender}"
            swWeight.text = "Massa corpórea: ${it.mass}"
            swHair.text = "Cor do cabelo: ${it.hairColor}"
            swSkin.text = "Cor da pele: ${it.skinColor}"
            swEye.text = "Cor dos olhos: ${it.eyeColor}"
            swBirth.text = "Ano de nascimento: ${it.birthYear}"
            swHome.text = "Planeta de origem: ${it.homeWorld}"
            swSpecie.text = "Espécie: ${it.species}"

            swFav.setOnClickListener { _ ->
                it.isFavorite = !it.isFavorite
                if (it.isFavorite){
                    swFav.setColorFilter(Color.RED)
                } else {
                    swFav.setColorFilter(Color.GRAY)
                }
            }

            if (it.isFavorite){
                swFav.setColorFilter(Color.RED)
            } else {
                swFav.setColorFilter(Color.GRAY)
            }

        }
    }
}