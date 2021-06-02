package com.knowledge.wikisw_luan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.knowledge.wikisw_luan.adapter.Adapter
import com.knowledge.wikisw_luan.models.Character

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_sw)
        val  adapter = Adapter(getList())
        recyclerView.adapter = adapter


    }

    private fun getChar(): List<Character> {
        return arrayListOf(
            Character("Yoda", "77", "Bald", "Green", "002 BBY", "Male", "Tatooine", "Unknow", "Unknow"),
            Character("Luke", "85", "Brown", "White", "250 BBY", "Male", "Tatooine", "Unknow", "Human"),
            Character("Anakin", "82", "Brown", "White", "220 BBY", "Male", "Tatooine", "Unknow", "Human"),
            Character("Palpatine", "65", "White", "Pale", "198 BBY", "Male", "Tatooine", "Unknow", "Human"),
            Character("Obi-Wan", "71", "Grey", "White", "198 BBY", "Male", "Tatooine", "Unknow", "Human"),
            Character("Leia", "58", "Black", "White", "198 BBY", "Female", "Tatooine", "Unknow", "Human"),

        )
    }

    private fun getList(): List<Character> {
        val list = arrayListOf<Character>()
        for (i in 0..87) {
            list.add(Character("Yoda$i", "47kg", "76cm", "Bald", "002 BBY", "Male", "Masculino", "Unknow", "Unknow"))
        }
        return list
    }

}