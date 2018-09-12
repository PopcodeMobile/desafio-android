package com.matheusfroes.swapi.ui.favorites

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.matheusfroes.swapi.R
import com.matheusfroes.swapi.extra.appInjector
import com.matheusfroes.swapi.extra.viewModelProvider
import com.matheusfroes.swapi.ui.peoplelist.PeopleAdapter
import com.matheusfroes.swapi.ui.persondetail.PersonDetailActivity
import kotlinx.android.synthetic.main.activity_people_list.*
import javax.inject.Inject

class BookmarkedPeopleActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: BookmarkedPeopleViewModel

    private val adapter = PeopleAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people_list)
        appInjector.inject(this)

        viewModel = viewModelProvider(viewModelFactory)

        viewModel.getBookmarkedPeople().observe(this, Observer { people ->
            if (people != null) {
                adapter.items = people
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
}
