package com.matheusfroes.swapi.ui.persondetail

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.matheusfroes.swapi.R
import com.matheusfroes.swapi.appInjector
import com.matheusfroes.swapi.viewModelProvider
import javax.inject.Inject

class PersonDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: PersonDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_detail)
        appInjector.inject(this)

        viewModel = viewModelProvider(viewModelFactory)
    }
}
