package com.albuquerque.starwarswiki.app.view.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.albuquerque.starwarswiki.R
import com.albuquerque.starwarswiki.app.extensions.*
import br.albuquerque.data.ui.PersonUI
import com.albuquerque.starwarswiki.app.viewmodel.PersonDetailViewModel
import com.albuquerque.starwarswiki.databinding.ActivityPersonDetailBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_person_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PersonDetailActivity : AppCompatActivity() {

    companion object {
        const val PERSON = "PERSON"
    }

    private val personDetailViewModel: PersonDetailViewModel by viewModel()
    private lateinit var binding: ActivityPersonDetailBinding
    private lateinit var _person: PersonUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_detail)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_person_detail)

        _person = (intent.extras!!.getSerializable(PERSON)!! as PersonUI)

        with(personDetailViewModel) {
            setPerson(_person)
            fetchData()
        }

        setupDataBinding()
        setupView()
        subscribeUI()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){

            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun setupDataBinding() {

        with(binding) {
            lifecycleOwner = this@PersonDetailActivity
            person = this@PersonDetailActivity._person
            species = personDetailViewModel.species.value
            homePlanet = personDetailViewModel.homePlanet.value
            isFav = _person.isFavorite
            executePendingBindings()
        }

    }

    private fun setupView() {

        setupToolbar(toolbar, _person.name) {
            setDisplayHomeAsUpEnabled(true)
        }

        fab.setOnClickListener {
            personDetailViewModel.handleFavorite(_person, 0)
        }

    }

    private fun subscribeUI() {
        with(personDetailViewModel) {

            homePlanet.observe(this@PersonDetailActivity) { planet ->
                planet?.let { binding.homePlanet = it }
            }

            species.observe(this@PersonDetailActivity) { species ->
                species?.let { binding.species = it }
            }

            onHandleFavorite.observe(this@PersonDetailActivity) { pair ->
                pair.first?.let {
                    binding.isFav = _person.isFavorite

                    if (pair.second.isNotBlank() || pair.second.isNotEmpty())
                        Snackbar.make(container, pair.second, Snackbar.LENGTH_LONG).success()

                } ?: kotlin.run {
                    Snackbar.make(container, pair.second, Snackbar.LENGTH_LONG).error()
                }
            }

            onStartLoading.observe(this@PersonDetailActivity) {
                progress.setVisible()
            }

            onFinishLoading.observe(this@PersonDetailActivity) {
                progress.setGone()
            }

        }

    }

}
