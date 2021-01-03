package com.example.starwars.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwars.R
import com.example.starwars.adapter.PeopleAdapter
import com.example.starwars.repository.RepositoryApi
import com.example.starwars.viewmodel.PeopleViewModel
import com.example.starwars.viewmodel.PeopleViewModelFactory
import kotlinx.android.synthetic.main.activity_inicio.*

class Inicio : AppCompatActivity() {

    private lateinit var viewModel: PeopleViewModel

    private val peopleAdapter by lazy { PeopleAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        //Adiciona Suporte Toolbar
        setSupportActionBar(toolbar)

        //Declara e Inicializa o Repositorio
        val repositoryApi = RepositoryApi()

        //Executa metodo que inicia e carrega o recyclerview
        setupRecyclerView()

        //Informa o repositorio ao Factory
        val viewModelFactory = PeopleViewModelFactory(repositoryApi)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PeopleViewModel::class.java)

        viewModel.getPeople()
        viewModel.myResponse.observe(this, Observer {response->
            peopleAdapter.setData(response)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)

        // obter id do icon search
        val search = menu?.findItem(R.id.id_search)
        val searchView = search?.actionView as SearchView
        searchView.queryHint = "Nome do personagem"

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                peopleAdapter.filter.filter(newText)
                return true
            }

        }

        )
        return super.onCreateOptionsMenu(menu)
    }

    // Pega o conteudo do Adapter e joga para o RecyclerView
    private fun setupRecyclerView(){
        recyclerView.adapter = peopleAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}