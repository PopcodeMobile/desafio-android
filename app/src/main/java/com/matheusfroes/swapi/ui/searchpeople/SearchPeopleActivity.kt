package com.matheusfroes.swapi.ui.searchpeople

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import com.matheusfroes.swapi.R
import com.matheusfroes.swapi.extra.appInjector
import com.matheusfroes.swapi.extra.viewModelProvider
import com.matheusfroes.swapi.ui.EndlessScrollListener
import com.matheusfroes.swapi.ui.persondetail.PersonDetailActivity
import kotlinx.android.synthetic.main.activity_search_people.*
import javax.inject.Inject

class SearchPeopleActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, SearchPeopleActivity::class.java))
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SearchPeopleViewModel
    private val adapter = PeopleSearchAdapter()

    private lateinit var scrollListener: EndlessScrollListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_people)
        appInjector.inject(this)

        viewModel = viewModelProvider(viewModelFactory)

        viewModel.searchResult.observe(this, Observer { person ->
            if (person != null) {
                adapter.items = person.toMutableList()
            }
        })

        adapter.personClickEvent = { personId ->
            PersonDetailActivity.start(this, personId)
        }

        val layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        rvSearchResult.layoutManager = layoutManager
        rvSearchResult.adapter = adapter

        scrollListener = object : EndlessScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                viewModel.searchPeople(page)
                Log.d("SWAPI", "Page: $page")
            }
        }
        rvSearchResult.addOnScrollListener(scrollListener)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setIconifiedByDefault(false)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                scrollListener.resetState()
                adapter.items.clear()
                viewModel.searchQuery = query
                viewModel.searchPeople()
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })

        return true
    }
}
