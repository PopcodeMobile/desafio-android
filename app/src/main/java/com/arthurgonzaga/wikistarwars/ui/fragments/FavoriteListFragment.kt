package com.arthurgonzaga.wikistarwars.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.core.view.isEmpty
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.arthurgonzaga.wikistarwars.R
import com.arthurgonzaga.wikistarwars.data.model.CharacterEntity
import com.arthurgonzaga.wikistarwars.databinding.FragmentDetailBinding
import com.arthurgonzaga.wikistarwars.databinding.FragmentFavoriteListBinding
import com.arthurgonzaga.wikistarwars.databinding.FragmentHomeBinding
import com.arthurgonzaga.wikistarwars.ui.adapters.CharacterAdapter
import com.arthurgonzaga.wikistarwars.ui.components.SpacingItemDecoration
import com.arthurgonzaga.wikistarwars.ui.util.navigateToDetailFragment
import com.arthurgonzaga.wikistarwars.viewmodel.FavoritesViewModel
import com.arthurgonzaga.wikistarwars.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


/**
 *
 *  This fragment will display the list of favorites characters
 *
 */
@AndroidEntryPoint
class FavoriteListFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteListBinding
    private val vm: FavoritesViewModel by viewModels()

    private lateinit var characterAdapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteListBinding.inflate(inflater)

        setupRecyclerView()
        observeChanges()

        return binding.root
    }

    private fun setupRecyclerView() {
        characterAdapter =
            CharacterAdapter(
                context = requireContext(),
                navigateToDetail = ::navigateToDetailFragment,
                favoriteCharacter = { id, isFavorite ->
                    if (!isFavorite) vm.unFavoriteCharacter(id)
                }
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
    }

    private fun observeChanges() {
        vm.favoritesCharacters.observe(viewLifecycleOwner) { pagingData ->
            viewLifecycleOwner.lifecycleScope.launch {
                characterAdapter.submitData(pagingData)

            }
        }
    }


    private fun navigateToDetailFragment(
        characterEntity: CharacterEntity,
        textView: TextView,
        imageButton: ImageButton,
        viewGroup: ViewGroup
    ) = navigateToDetailFragment(
        characterEntity,
        textView,
        imageButton,
        viewGroup,
        R.id.goToDetailFragmentFromFavorites
    )

    companion object {
        private const val TAG = "FavoriteListFragment"
    }
}