package com.arthurgonzaga.wikistarwars.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.arthurgonzaga.wikistarwars.R
import com.arthurgonzaga.wikistarwars.databinding.FragmentHomeBinding
import com.arthurgonzaga.wikistarwars.ui.adapters.CharacterAdapter
import com.arthurgonzaga.wikistarwars.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.recyclerview.widget.LinearLayoutManager





/**
 *
 *  This fragment is the initial fragment of the backstack.
 *
 *  It will display a at the top header and a recyclerview containing
 *  some information about the characters
 *
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val vm: HomeViewModel by viewModels()

    @Inject lateinit var characterAdapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.recyclerView.apply {
            adapter = characterAdapter
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        vm.characters.observe(viewLifecycleOwner){ pagingData ->
            viewLifecycleOwner.lifecycleScope.launch {
                characterAdapter.submitData(pagingData)
            }
        }

        binding.row.favoriteListButton.setOnClickListener {
            findNavController().navigate(R.id.goToFavoritesListFragment)
        }
    }
}