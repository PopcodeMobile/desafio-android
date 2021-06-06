package com.knowledge.wikisw_luan.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.knowledge.wikisw_luan.R
import com.knowledge.wikisw_luan.adapter.Adapter
import com.knowledge.wikisw_luan.adapter.ClickWikiListener
import com.knowledge.wikisw_luan.models.CharacterModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), ClickWikiListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainViewModel: MainViewModel by viewModel()
        val search = findViewById<SearchView>(R.id.search_view)
        val characterList = getChar()
        var showFavorite = false
        val filter = findViewById<ImageView>(R.id.filter_button)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_sw)
        val adapter = Adapter(listener = this)
        adapter.updateList(characterList)
        recyclerView.adapter = adapter
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                if (text != null) {
                    adapter.updateList(characterList.filter { it.name.contains(text, true) })
                } else {
                    adapter.updateList(characterList)
                }
                return true
            }
        })
        search.setOnClickListener { search.onActionViewExpanded() }
        filter.setOnClickListener {
            showFavorite = !showFavorite
            if (showFavorite) {
                adapter.updateList(characterList.filter { it.isFavorite })
            } else {
                adapter.updateList(characterList)
            }
        }
        mainViewModel.getCharacters()
    }

    private fun getChar(): List<CharacterModel> {
        return arrayListOf(
            CharacterModel(
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
            CharacterModel(
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
            CharacterModel(
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
            CharacterModel(
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
            CharacterModel(
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
            CharacterModel(
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

    override fun onListClick(character: CharacterModel) {
        val charInfo = Intent(this, CharActivity::class.java)
        charInfo.putExtra("CharInfo", character)
        startActivity(charInfo)
    }

    override fun onFavClick(character: CharacterModel) {
        Toast.makeText(this, "${character.name} foi favoritado!", Toast.LENGTH_SHORT).show()
    }

}