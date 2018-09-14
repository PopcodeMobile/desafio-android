package com.matheusfroes.swapi.ui.bookmarks

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.matheusfroes.swapi.R
import com.matheusfroes.swapi.extra.appInjector
import com.matheusfroes.swapi.extra.viewModelProvider
import com.matheusfroes.swapi.ui.peoplelist.PeopleAdapter
import com.matheusfroes.swapi.ui.persondetail.PersonDetailActivity
import kotlinx.android.synthetic.main.activity_bookmarked_people.*
import javax.inject.Inject

class BookmarkedPeopleActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, BookmarkedPeopleActivity::class.java))
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: BookmarkedPeopleViewModel

    private val adapter = PeopleAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmarked_people)
        appInjector.inject(this)

        viewModel = viewModelProvider(viewModelFactory)

        viewModel.getBookmarkedPeople().observe(this, Observer { people ->
            if (people != null) {
                adapter.items = people
                checkEmptyList()
            }
        })

        adapter.personClickEvent = { personId ->
            PersonDetailActivity.start(this, personId)
        }

        adapter.toggleBookmarkPersonEvent = { personId ->
            viewModel.unbookmarkPerson(personId)
        }

        rvPeople.layoutManager = LinearLayoutManager(this)
        rvPeople.adapter = adapter
    }

    private fun checkEmptyList() {
        if (adapter.itemCount == 0) {
            showEmptyView()
        } else {
            hideEmptyView()
        }
    }

    private fun showEmptyView() {
        emptyViewBookmarks.visibility = View.VISIBLE
    }

    private fun hideEmptyView() {
        emptyViewBookmarks.visibility = View.GONE
    }
}
