package com.matheusfroes.swapi.ui.persondetail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.matheusfroes.swapi.R
import com.matheusfroes.swapi.data.model.Person
import com.matheusfroes.swapi.extra.Result
import com.matheusfroes.swapi.extra.appInjector
import com.matheusfroes.swapi.extra.toast
import com.matheusfroes.swapi.extra.viewModelProvider
import kotlinx.android.synthetic.main.activity_person_detail.*
import javax.inject.Inject

class PersonDetailActivity : AppCompatActivity() {
    companion object {
        const val PERSON_ID = "com.matheusfroes.swapi.person_id"

        fun start(context: Context, personId: Long) {
            val intent = Intent(context, PersonDetailActivity::class.java)
            intent.putExtra(PERSON_ID, personId)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: PersonDetailViewModel

    private val personId: Long by lazy { intent.getLongExtra(PERSON_ID, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_detail)
        appInjector.inject(this)

        intent ?: return

        viewModel = viewModelProvider(viewModelFactory)
        viewModel.personId = personId

        viewModel.personDetailObservable.observe(this, Observer { result ->
            when (result) {
                is Result.Complete -> {
                    updateUI(result.data)
                    hideLoadingIndicator()
                }
                is Result.InProgress -> {
                    showLoadingIndicator()
                }
                is Result.Error -> {
                    Log.e("SWAPI", "Erro ao exibir personagem", result.error)
                    hideLoadingIndicator()
                }
            }
        })

        btnBookmarkPerson.setOnClickListener {
            viewModel.toggleBookmarkPerson()
        }

        viewModel.unbookmarkedPersonEvent.observe(this, Observer {
            btnBookmarkPerson.setImageResource(R.drawable.ic_bookmark)
        })

        viewModel.bookmarkedPersonEvent.observe(this, Observer { bookmarkResponse ->
            if (bookmarkResponse != null) {
                if (bookmarkResponse.bookmarked) {
                    btnBookmarkPerson.setImageResource(R.drawable.ic_bookmarked)
                } else {
                    btnBookmarkPerson.setImageResource(R.drawable.ic_bookmark)
                }
                toast(bookmarkResponse.message)
            }
        })

        viewModel.homeworldObservable.observe(this, Observer { homeworld ->
            tvHomeworld.text = homeworld
        })

        viewModel.speciesObservable.observe(this, Observer { species ->
            tvSpecies.text = species
        })
    }

    private fun updateUI(person: Person) {
        title = person.name

        tvName.text = person.name
        tvHeight.text = person.height
        tvMass.text = person.mass
        tvHairColor.text = person.hairColor
        tvSkinColor.text = person.skinColor
        tvEyeColor.text = person.eyeColor
        tvBirthYear.text = person.birthYear
        tvGender.text = person.gender

        if (person.isBookmarked) {
            btnBookmarkPerson.setImageResource(R.drawable.ic_bookmarked)
        } else {
            btnBookmarkPerson.setImageResource(R.drawable.ic_bookmark)
        }
    }

    private fun showLoadingIndicator() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoadingIndicator() {
        progressBar.visibility = View.GONE
    }
}
