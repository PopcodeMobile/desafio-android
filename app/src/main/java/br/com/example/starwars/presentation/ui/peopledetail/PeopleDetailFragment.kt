package br.com.example.starwars.presentation.ui.peopledetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import br.com.example.starwars.R
import br.com.example.starwars.databinding.FragmentDetailPeopleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PeopleDetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailPeopleBinding
    private val viewModel: PeopleDetailViewModel by viewModels()
    private val args by navArgs<PeopleDetailFragmentArgs>()
    private val people by lazy { args.people }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentDetailPeopleBinding.inflate(inflater, container, false)

        binding.people = people
        subscribeUi()
        setClickListeners()
        alteredIcon()
        return binding.root
    }

    private fun setClickListeners() {
        with(binding) {
            addFavorite.setOnClickListener {
                this@PeopleDetailFragment.people.favorite =
                    this@PeopleDetailFragment.people.favorite.not()
                viewModel.favoritePerson(this@PeopleDetailFragment.people)
                alteredIcon()
            }
        }
    }

    private fun subscribeUi() {
        people.homeWorld?.let { homeWorld ->
            people.species?.let { specie ->
                viewModel.getPlanetAndSpecie(homeWorld, specie)
            }
        }

        with(viewModel) {
            specie.observe(viewLifecycleOwner) {
                binding.specie.text = it.name
            }
            planet.observe(viewLifecycleOwner) {
                binding.homeWorld.text = it.name
            }
            loading.observe(viewLifecycleOwner) {
                if (it) {
                    binding.includeLoading.visibility = View.VISIBLE
                } else {
                    binding.includeLoading.visibility = View.GONE
                }
            }
        }
    }

    private fun alteredIcon() {
        if (people.favorite) {
            binding.addFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            binding.addFavorite.setImageResource(R.drawable.ic_favorite_border)
        }
    }
}