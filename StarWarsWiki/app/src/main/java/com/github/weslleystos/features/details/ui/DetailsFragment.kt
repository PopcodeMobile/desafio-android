package com.github.weslleystos.features.details.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.github.weslleystos.MainActivity
import com.github.weslleystos.R
import com.github.weslleystos.databinding.FragmentDetailBinding
import com.github.weslleystos.features.details.viewmodel.DetailsViewModel
import com.github.weslleystos.shared.entities.People
import com.github.weslleystos.shared.entities.Planet
import com.github.weslleystos.shared.entities.Specie

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val detailsViewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val people = arguments?.get("people") as People

        (activity as MainActivity).supportActionBar?.title = people.name
        binding.people = people

        detailsViewModel.specieLiveData.observe(viewLifecycleOwner, specieObserver)
        detailsViewModel.planetLiveData.observe(viewLifecycleOwner, planetObserver)

        if (people.homeWorld.isNotEmpty()) {
            detailsViewModel.getHomeWorld(people.homeWorld)
        }

        if (!people.specie.isNullOrEmpty()) {
            detailsViewModel.getSpecie(people.specie!!)
        }
    }

    private val specieObserver = Observer<Pair<Boolean, Specie?>> { response ->
        when {
            response.first -> {
                binding.specie.visibility = View.VISIBLE
                binding.specieName.text = response.second?.name
            }
            else -> {
                Toast.makeText(
                    context,
                    getString(R.string.species_not_found),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private val planetObserver = Observer<Pair<Boolean, Planet?>> { response ->
        when {
            response.first -> {
                binding.planet.visibility = View.VISIBLE
                binding.planetName.text = response.second?.name
            }
            else -> {
                Toast.makeText(
                    context,
                    getString(R.string.planets_not_found),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}