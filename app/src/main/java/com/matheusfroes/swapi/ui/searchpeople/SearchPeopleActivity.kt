package com.matheusfroes.swapi.ui.searchpeople

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import com.matheusfroes.swapi.R
import com.matheusfroes.swapi.extra.Result
import com.matheusfroes.swapi.extra.appInjector
import com.matheusfroes.swapi.extra.toast
import com.matheusfroes.swapi.extra.viewModelProvider
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_people)
        appInjector.inject(this)

        viewModel = viewModelProvider(viewModelFactory)

        viewModel.search.observe(this, Observer { people ->
            adapter.submitList(people)
        })

        viewModel.networkState.observe(this, Observer { networkState ->
            when (networkState) {
                is Result.Complete -> {
                    hideLoadingIndicator()
                }
                is Result.InProgress -> {
                    showLoadingIndicator()
                }
                is Result.Error -> {
                    toast("Error fetching people list")
                    hideLoadingIndicator()
                }
            }
        })

        adapter.personClickEvent = { personId ->
            PersonDetailActivity.start(this, personId)
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        rvSearchResult.layoutManager = layoutManager
        rvSearchResult.adapter = adapter
    }

    private fun showLoadingIndicator() {
        if (adapter.itemCount == 0) {
            initialLoadingIndicator.visibility = View.VISIBLE
        } else {
            pageLoadingIndicator.visibility = View.VISIBLE
        }
    }

    private fun hideLoadingIndicator() {
        initialLoadingIndicator.visibility = View.GONE
        pageLoadingIndicator.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setIconifiedByDefault(false)
        searchView.requestFocus()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchPeople(query)
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
