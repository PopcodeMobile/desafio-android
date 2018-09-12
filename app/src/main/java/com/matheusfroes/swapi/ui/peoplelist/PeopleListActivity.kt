package com.matheusfroes.swapi.ui.peoplelist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.View
import com.matheusfroes.swapi.*
import com.matheusfroes.swapi.ui.persondetail.PersonDetailActivity
import kotlinx.android.synthetic.main.activity_people_list.*
import javax.inject.Inject

class PeopleListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: PeopleListViewModel

    private val adapter = PeopleAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people_list)
        appInjector.inject(this)

        viewModel = viewModelProvider(viewModelFactory)

        viewModel.peopleObservable.observe(this, Observer { result ->
            when (result) {
                is Result.Complete -> {
                    adapter.items = result.data
                    hideLoadingIndicator()
                }
                is Result.InProgress -> {
                    showLoadingIndicator()
                }
                is Result.Error -> {
                    hideLoadingIndicator()
                    toast("Erro ao baixar dados")
                }
            }
        })

        viewModel.getPeople()

        adapter.personClickEvent = { personId ->
            PersonDetailActivity.start(this, personId)
        }

        adapter.toggleBookmarkPersonEvent = { personId ->

        }

        val layoutManager = LinearLayoutManager(this)
        rvPeople.layoutManager = layoutManager
        rvPeople.adapter = adapter

        rvPeople.addOnScrollListener(object : EndlessScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                viewModel.getPeople(page)
                Log.d("SWAPI", "Page = $page")
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    private fun showLoadingIndicator() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoadingIndicator() {
        progressBar.visibility = View.GONE
    }
}
