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
import com.knowledge.wikisw_luan.databinding.ActivityMainBinding
import com.knowledge.wikisw_luan.databinding.CharInfoBinding
import com.knowledge.wikisw_luan.models.CharacterModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), ClickWikiListener {

    val adapter = Adapter(listener = this)
    val listchar = arrayListOf<CharacterModel>()
    val mainViewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mainViewModel.state.observe(this@MainActivity, {handleState(it)})
        val characterList = listchar
        var showFavorite = false
        adapter.updateList(characterList)
        binding.rvSw.adapter = adapter
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
        binding.searchView.setOnClickListener { binding.searchView.onActionViewExpanded() }
        binding.filterButton.setOnClickListener {
            showFavorite = !showFavorite
            if (showFavorite) {
                adapter.updateList(characterList.filter { it.isFavorite })
            } else {
                adapter.updateList(characterList)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.getCharacters()
    }

    private fun handleState(state: Any) {
        when (state) {
            is ShowCharacters -> {
                listchar.clear()
                listchar.addAll(state.list)
                adapter.updateList(state.list)
            }
        }

    }

    override fun onListClick(character: CharacterModel) {
        val charInfo = Intent(this, CharActivity::class.java)
        charInfo.putExtra("CharInfo", character)
        startActivity(charInfo)
    }

    override fun onFavClick(character: CharacterModel) {
        mainViewModel.getFav(character.name, character.isFavorite)
    }

}