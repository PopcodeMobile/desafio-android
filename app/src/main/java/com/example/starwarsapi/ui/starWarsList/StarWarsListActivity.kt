package com.example.starwarsapi.ui.starWarsList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwarsapi.ui.starWarsInfo.StarWarsInfoActivity
import com.example.starwarsapi.R
import kotlinx.android.synthetic.main.activity_starwars_list.*

class StarWarsListActivity : AppCompatActivity() {

    private lateinit var viewModel: StarWarsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starwars_list)

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        viewModel = ViewModelProvider (this).get(StarWarsListViewModel::class.java)

        initUI()
    }

    private fun initUI(){
        recyclerview_starwars_list.layoutManager= LinearLayoutManager(this)
        recyclerview_starwars_list.adapter = StarWarsListAdapter{
            val intent = Intent(this, StarWarsInfoActivity::class.java)
            intent.putExtra("id", it) //passa o id da posição que foi clicado
            startActivity(intent)
        }

        viewModel.getStarWarsList()

        viewModel.starWarsList.observe( this, Observer { list ->

            (recyclerview_starwars_list.adapter as StarWarsListAdapter).setData(list)

        } )

    }
}