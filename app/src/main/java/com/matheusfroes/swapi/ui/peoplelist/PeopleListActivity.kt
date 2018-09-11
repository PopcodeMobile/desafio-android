package com.matheusfroes.swapi.ui.peoplelist

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.matheusfroes.swapi.R
import com.matheusfroes.swapi.appInjector
import javax.inject.Inject

class PeopleListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: PeopleListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people_list)
        appInjector.inject(this)
    }
}
