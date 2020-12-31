package com.example.starwars.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.example.starwars.R
import kotlinx.android.synthetic.main.activity_inicio.*

class Inicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        setSupportActionBar(toolbar)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)

        // obter id do icon search
        val search = menu?.findItem(R.id.id_search)
        val searchView = search?.actionView as SearchView
        searchView.queryHint = "Digite Aqui"

        return super.onCreateOptionsMenu(menu)
    }
}