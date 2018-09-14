package com.matheusfroes.swapi.ui.peoplelist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.matheusfroes.swapi.R
import com.matheusfroes.swapi.extra.Result
import com.matheusfroes.swapi.extra.appInjector
import com.matheusfroes.swapi.extra.toast
import com.matheusfroes.swapi.extra.viewModelProvider
import com.matheusfroes.swapi.ui.bookmarks.BookmarkedPeopleActivity
import com.matheusfroes.swapi.ui.persondetail.PersonDetailActivity
import com.matheusfroes.swapi.ui.searchpeople.SearchPeopleActivity
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

        // ViewModel observers
        viewModel.people.observe(this, Observer { people ->
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

        viewModel.bookmarkEvent.observe(this, Observer { bookmarkEvent ->
            toast(bookmarkEvent?.message)
        })


        // Adapter click events
        adapter.personClickEvent = { personId ->
            PersonDetailActivity.start(this, personId)
        }

        adapter.toggleBookmarkPersonEvent = { personId ->
            viewModel.toggleBookmark(personId)
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        rvPeople.layoutManager = layoutManager
        rvPeople.adapter = adapter
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
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bookmarked -> {
                BookmarkedPeopleActivity.start(this)
            }
            R.id.search -> {
                SearchPeopleActivity.start(this)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
