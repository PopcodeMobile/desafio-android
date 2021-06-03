package com.knowledge.wikisw_luan.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.knowledge.wikisw_luan.R
import com.knowledge.wikisw_luan.adapter.Adapter
import com.knowledge.wikisw_luan.adapter.ClickWikiListener
import com.knowledge.wikisw_luan.models.Character

class MainActivity : AppCompatActivity(), ClickWikiListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_sw)
        val adapter = Adapter(getList(), listener = this)
        recyclerView.adapter = adapter


    }

    private fun getChar(): List<Character> {
        return arrayListOf(
            Character(
                "Yoda",
                "77",
                "Bald",
                "Green",
                "002 BBY",
                "Male",
                "Tatooine",
                "Unknow",
                "Unknow",
                "human"
            ),
            Character(
                "Luke",
                "85",
                "Brown",
                "White",
                "250 BBY",
                "Male",
                "Tatooine",
                "Unknow",
                "Human",
                "Dalek"
            ),
            Character(
                "Anakin",
                "82",
                "Brown",
                "White",
                "220 BBY",
                "Male",
                "Tatooine",
                "Unknow",
                "Human",
                "Gallifreyan"
            ),
            Character(
                "Palpatine",
                "65",
                "White",
                "Pale",
                "198 BBY",
                "Male",
                "Tatooine",
                "Unknow",
                "Human",
                "Imp"
            ),
            Character(
                "Obi-Wan",
                "71",
                "Grey",
                "White",
                "198 BBY",
                "Male",
                "Tatooine",
                "Unknow",
                "Human",
                "Marcian"
            ),
            Character(
                "Leia",
                "58",
                "Black",
                "White",
                "198 BBY",
                "Female",
                "Tatooine",
                "Unknow",
                "Human",
                "Human"
            ),

            )
    }

    private fun getList(): List<Character> {
        val list = arrayListOf<Character>()
        for (i in 0..87) {
            list.add(
                Character(
                    "Yoda$i",
                    "47kg",
                    "76cm",
                    "Bald",
                    "002 BBY",
                    "Male",
                    "Masculino",
                    "Unknow",
                    "Unknow",
                    "Yoda"
                )
            )
        }
        return list
    }

    override fun onListClick(character: Character) {
        val charInfo = Intent(this, CharActivity::class.java)
        charInfo.putExtra("CharInfo", character)
        startActivity(charInfo)
    }

    override fun onFavClick(character: Character) {
        Toast.makeText(this, "${character.name} foi favoritado!", Toast.LENGTH_SHORT).show()
    }

}