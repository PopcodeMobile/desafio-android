package com.arthurgonzaga.wikistarwars.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.arthurgonzaga.wikistarwars.R
import com.arthurgonzaga.wikistarwars.databinding.FragmentHomeBinding
import com.arthurgonzaga.wikistarwars.ui.adapters.CharacterAdapter
import com.arthurgonzaga.wikistarwars.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.recyclerview.widget.LinearLayoutManager
import com.arthurgonzaga.wikistarwars.data.model.CharacterEntity
import com.arthurgonzaga.wikistarwars.ui.components.SpacingItemDecoration


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

    lateinit var characterAdapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        characterAdapter =
            CharacterAdapter(
                context = requireContext(),
                navigateToDetail = ::navigateToDetailFragment,
                favoriteCharacter = vm::favoriteCharacter
            )

        binding.recyclerView.apply {
            adapter = characterAdapter
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }

            val space = requireContext().resources.getDimensionPixelSize(R.dimen.normal)
            val spanCount = requireContext().resources.getInteger(R.integer.rv_column_count)

            addItemDecoration(SpacingItemDecoration(spanCount = spanCount, spacing = space))
        }
        return binding.root
    }

    private fun navigateToFavoriteFragment() {
        findNavController().navigate(R.id.goToFavoritesListFragment)
    }

    private fun navigateToDetailFragment(
        characterEntity: CharacterEntity,
        textView: TextView,
        imageButton: ImageButton,
        viewGroup: ViewGroup
    ) {

        val extras = FragmentNavigatorExtras(
            textView to "heading_big",
            imageButton to "favorite_btn_big",
            viewGroup to "background"
        )

        val args = bundleOf("character" to characterEntity)
        findNavController().navigate(
            R.id.goToDetailFragment,
            args,
            null,
            extras
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        vm.characters.observe(viewLifecycleOwner) { pagingData ->
            viewLifecycleOwner.lifecycleScope.launch {
                characterAdapter.submitData(pagingData)
            }
        }

        binding.row.favoriteListButton.setOnClickListener { _ ->
            navigateToFavoriteFragment()
        }
    }
}