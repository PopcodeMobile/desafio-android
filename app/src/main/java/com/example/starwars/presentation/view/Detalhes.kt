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
        textHeightCompanion.text = "Height: $heightCompanion"
        textGenderCompanion.text = "Gender: $genderCompanion"
        textHair_colorCompanion.text = "Hair Color: $hair_colorCompanion"
        textSkin_colorCompanion.text = "Skin Color: $skin_colorCompanion"
        textEye_colorCompanion.text = "Eve Color: $eye_colorCompanion"
        textBirth_yearCompanion.text = "Birth Year: $birth_yearCompanion"
        textMassCompanion.text = massCompanion

    }
}