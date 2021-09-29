package com.arthurgonzaga.wikistarwars.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.arthurgonzaga.wikistarwars.R
import com.arthurgonzaga.wikistarwars.databinding.FragmentHomeBinding
import com.arthurgonzaga.wikistarwars.ui.adapters.CharacterAdapter
import com.arthurgonzaga.wikistarwars.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.arthurgonzaga.wikistarwars.data.model.CharacterEntity
import com.arthurgonzaga.wikistarwars.ui.components.MyThemes
import com.arthurgonzaga.wikistarwars.ui.components.SpacingItemDecoration
import com.arthurgonzaga.wikistarwars.ui.util.navigateToDetailFragment


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

        setupRecyclerView()
        setupSearchBar()
        setupThemeChanger()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeChanges()
    }

    private fun setupRecyclerView(){
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
    }

    private fun setupSearchBar(){
        binding.row.searchBar.setOnEditorActionListener { textView, action, _ ->
            if(action == EditorInfo.IME_ACTION_SEARCH){

                vm.search(textView.text.toString())

                return@setOnEditorActionListener true
            }
            false
        }

        binding.row.searchBarLayout.setEndIconOnClickListener { _ ->
            binding.row.searchBar.setText("")
            vm.search("")
        }

        binding.row.favoriteListButton.setOnClickListener { _ ->
            navigateToFavoritesFragment()
        }
    }

    private fun setupThemeChanger(){
        val sharedPref = requireContext().getSharedPreferences(getString(R.string.theme_key), Context.MODE_PRIVATE)

        val nextTheme = when(binding.header.changeColor.backgroundTintList?.defaultColor){
            requireContext().getColor(R.color.yellow) -> MyThemes.YELLOW
            requireContext().getColor(R.color.blue) -> MyThemes.BLUE
            requireContext().getColor(R.color.red) -> MyThemes.RED
            else -> MyThemes.YELLOW
        }

        binding.header.changeColor.setOnClickListener { _ ->
            with(sharedPref.edit()){
                putInt("theme", nextTheme.ordinal)
                commit()
            }
            requireActivity().recreate()
        }
    }

    private fun observeChanges(){
        vm.characters.observe(viewLifecycleOwner) { pagingData ->
            viewLifecycleOwner.lifecycleScope.launch {
                characterAdapter.submitData(pagingData)
            }
        }

        vm.newFavorite.observe(viewLifecycleOwner){ isSuccessful ->
            Log.d(TAG, "isSuccessful: $isSuccessful")
            if(isSuccessful == true){
                Toast.makeText(requireContext(), R.string.favorite_success, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToFavoritesFragment() {
        findNavController().navigate(R.id.goToFavoritesListFragment)
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
        R.id.goToDetailFragmentFromHome
    )


    companion object {
        private const val TAG = "HomeFragment"
    }
}