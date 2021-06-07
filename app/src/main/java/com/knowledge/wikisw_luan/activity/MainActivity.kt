package com.knowledge.wikisw_luan.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.knowledge.wikisw_luan.R
import com.knowledge.wikisw_luan.activity.SwState.ShowCharacters
import com.knowledge.wikisw_luan.adapter.Adapter
import com.knowledge.wikisw_luan.adapter.ClickWikiListener
import com.knowledge.wikisw_luan.models.CharacterModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), ClickWikiListener {

    val adapter = Adapter(listener = this)
    val listchar = arrayListOf<CharacterModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainViewModel: MainViewModel by viewModel()
        mainViewModel.state.observe(this@MainActivity, {handleState(it)})
        val search = findViewById<SearchView>(R.id.search_view)
        val characterList = listchar
        var showFavorite = false
        val filter = findViewById<ImageView>(R.id.filter_button)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_sw)
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

    private fun handleState(state: Any) {
        when (state) {
            is ShowCharacters -> {
                adapter.updateList(state.list)
                listchar.addAll(state.list)
            }
        }

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