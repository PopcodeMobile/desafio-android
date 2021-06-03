package com.knowledge.wikisw_luan.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.knowledge.wikisw_luan.R
import com.knowledge.wikisw_luan.models.Character

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

        val charInfo = intent.getParcelableExtra("CharInfo") as? Character
        charInfo?.let {
            swName.text = it.name
            swHeight.text = "Altura: ${it.height}"
            swGender.text = "Gênero: ${it.gender}"
            swWeight.text = "Massa corpórea: ${it.mass}"
            swHair.text = "Cor do cabelo: ${it.hair_color}"
            swSkin.text = "Cor da pele: ${it.skin_color}"
            swEye.text = "Cor dos olhos: ${it.eye_color}"
            swBirth.text = "Ano de nascimento: ${it.birth_year}"
            swHome.text = "Planeta de origem: ${it.homeworld}"
            swSpecie.text = "Espécie: ${it.species}"

        }
    }
}