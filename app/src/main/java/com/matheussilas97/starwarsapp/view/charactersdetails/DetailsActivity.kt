package com.matheussilas97.starwarsapp.view.charactersdetails

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.matheussilas97.starwarsapp.R
import com.matheussilas97.starwarsapp.database.model.FavoriteModel
import com.matheussilas97.starwarsapp.databinding.ActivityDetailsBinding
import com.matheussilas97.starwarsapp.utils.BaseActivity
import com.matheussilas97.starwarsapp.utils.Constants

class DetailsActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var viewModel: CharactersDetailsViewModel

    private var listSpecies = mutableListOf<String>()

    private var urlCharacter = ""
    private var nameCharacter = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[CharactersDetailsViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            setDetails(extras.getString(Constants.URL, ""))
        } else {
            showToast(getString(R.string.error))
            onBackPressed()
        }

        onClick()
        observer()
    }

    private fun setStarFavorite(url: String) {
        if (viewModel.isFavorite(url)) {
            binding.favorite.setImageResource(R.drawable.ic_star)
        } else {
            binding.favorite.setImageResource(R.drawable.ic_star2)
        }
    }

    private fun changeFavorite() {
        if (viewModel.isFavorite(urlCharacter)) {
            viewModel.deleteFavorite(binding.txtName.text.toString())
            binding.favorite.setImageResource(R.drawable.ic_star2)
        } else {
            saveFavorite()
        }
    }

    private fun setDetails(url: String) {
        viewModel.getDetails(url, this).observe(this, Observer {
            if (it != null) {
                binding.txtName.text = it.name
                binding.txtGender.text = it.gender
                binding.txtHeight.text = it.height
                binding.txtMass.text = it.mass
                binding.txtHairColor.text = it.hairColor
                binding.txtEyeColor.text = it.eyeColor
                binding.txtSkinColor.text = it.skinColor
                binding.txtYear.text = it.birthYear
                binding.txtHomeworld.text = ""

                nameCharacter = it.name
                urlCharacter = it.url
                setStarFavorite(it.url)

                if (it.homeworld.isNotEmpty()) {
                    getHomeWolrd(it.homeworld)
                } else {
                    binding.txtHomeworld.text = getString(R.string.no_data)
                }

                if (!it.species.isNullOrEmpty()) {
                    getSpeciesUrl(it.species)
                } else {
                    setNoResultAdapter(binding.recyclerSpecies, getString(R.string.no_species))
                }
            }
        })
    }

    private fun getSpeciesUrl(urlList: List<String>) {
        for (i in urlList) {
            getSpecies(i)
        }
    }

    private fun getSpecies(url: String) {
        viewModel.getSpecies(url).observe(this, Observer {
            if (it != null) {
                listSpecies.add(it.name)
                buildSpeciesList(listSpecies)
            }
        })
    }

    private fun buildSpeciesList(list: List<String>) {
        val adapter = SpeciesAdapter()
        binding.recyclerSpecies.layoutManager = LinearLayoutManager(this)
        binding.recyclerSpecies.adapter = adapter
        adapter.updateTask(list)
    }

    private fun getHomeWolrd(url: String) {
        viewModel.getHomeWorld(url).observe(this, Observer {
            if (it != null) {
                binding.txtHomeworld.text = it.name
            }
        })
    }

    private fun saveFavorite() {
        viewModel.postFavorite(binding.txtName.text.toString()).observe(this, Observer {
            showToast(it)
        })

    }

    private fun observer() {
        viewModel.saveStatus.observe(this, Observer {
            if (it) {
                val model = FavoriteModel(
                    binding.txtName.text.toString(),
                    urlCharacter
                )
                viewModel.saveClass(model)
                binding.favorite.setImageResource(R.drawable.ic_star)
            }
        })
    }

    private fun onClick() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.favorite.setOnClickListener {
            changeFavorite()
        }
    }

}