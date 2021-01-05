package com.example.starwars.presentation.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwars.R
import com.example.starwars.adapter.PeopleAdapter
import com.example.starwars.data.room.ResultEntity
import com.example.starwars.repository.RepositoryApi
import com.example.starwars.viewmodel.PeopleViewModel
import com.example.starwars.viewmodel.PeopleViewModelFactory
import com.example.starwars.viewmodel.PeopleViewModelRoom
import kotlinx.android.synthetic.main.activity_inicio.*

class Inicio : AppCompatActivity() {

    // Declarando ViewModel Room
    lateinit var peopleViewModelRoom: PeopleViewModelRoom

    // Declarando ViewModel Api
    private lateinit var viewModel: PeopleViewModel

    // Adapter
    private val peopleAdapter by lazy { PeopleAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        peopleViewModelRoom = ViewModelProvider(this).get(PeopleViewModelRoom::class.java)

        //Adiciona Suporte Toolbar
        setSupportActionBar(toolbar)

        //Executa metodo que inicia e carrega o recyclerview
        setupRecyclerView()

        /*
      Verifica conectividade e procede com uma ação
       */
      if (isNeworkAvailable()){
         acaoComInternet()
      }else{
         acaoSemInternet()
      }
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

    //METODOS UTEIS

    //Caso esteja com Internet
    fun acaoComInternet(){
        //Declara e Inicializa o Repositorio da Api
        val repositoryApi = RepositoryApi()
        //Informa o repositorio ao Factory
        val viewModelFactory = PeopleViewModelFactory(repositoryApi)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PeopleViewModel::class.java)
        //Realiza e retorna o resultado da api
        viewModel.getPeople()
        peopleViewModelRoom.deleteAllResults()
        viewModel.myResponse.observe(this, Observer {response->
            peopleAdapter.setData(response)
                    for(result in response){
                         val resultEntity = ResultEntity(0, result.name, result.height, result.gender, result.mass, result.hair_color,
                                 result.skin_color, result.eye_color, result.birth_year, result.homeworld)
                           peopleViewModelRoom.addResult(resultEntity)
                    }
        })
    }

    //Caso esteja sem internet
    fun acaoSemInternet(){
        Toast.makeText(this,"Sem internet, verifique sua conexão", Toast.LENGTH_SHORT).show()
        //ROOM
        peopleViewModelRoom.readAllData.observe(this, Observer { result ->
            peopleAdapter.setData(result)
        })
    }

    // Pega o conteudo do Adapter e joga para o RecyclerView
    private fun setupRecyclerView(){
        recyclerView.adapter = peopleAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    //VERIFICA A CONEXAO COM A INTERNET(Wifi e Movel)
    private fun isNeworkAvailable(): Boolean{
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifi: NetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobi: NetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        if (wifi.isConnectedOrConnecting || mobi.isConnectedOrConnecting){
            return true
        }else return false
    }

    override fun onBackPressed() {
        finish()
    }

}