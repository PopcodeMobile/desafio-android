package com.example.starwars.presentation.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.starwars.R
import com.example.starwars.adapter.PeopleAdapter
import com.example.starwars.adapter.PeopleAdapter.Companion.birth_yearCompanion
import com.example.starwars.adapter.PeopleAdapter.Companion.eye_colorCompanion
import com.example.starwars.adapter.PeopleAdapter.Companion.genderCompanion
import com.example.starwars.adapter.PeopleAdapter.Companion.hair_colorCompanion
import com.example.starwars.adapter.PeopleAdapter.Companion.heightCompanion
import com.example.starwars.adapter.PeopleAdapter.Companion.massCompanion
import com.example.starwars.adapter.PeopleAdapter.Companion.nameCompanion
import com.example.starwars.adapter.PeopleAdapter.Companion.skin_colorCompanion
import kotlinx.android.synthetic.main.activity_detalhes.*

class Detalhes : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)

        textNameCompanion.text = nameCompanion
        textHeightCompanion.text = "Altura: $heightCompanion"
        textGenderCompanion.text = "Genero: $genderCompanion"
        textHair_colorCompanion.text = "Cor do Cabelo: $hair_colorCompanion"
        textSkin_colorCompanion.text = "Cor da Pele: $skin_colorCompanion"
        textEye_colorCompanion.text = "Cor dos Olhos: $eye_colorCompanion"
        textBirth_yearCompanion.text = "Ano de Nascimento: $birth_yearCompanion"
        textMassCompanion.text = "Peso: $massCompanion"

    }
}