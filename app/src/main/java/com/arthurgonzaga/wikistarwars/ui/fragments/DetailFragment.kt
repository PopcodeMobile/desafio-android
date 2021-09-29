package com.arthurgonzaga.wikistarwars.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.trace
import androidx.core.view.forEach
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.arthurgonzaga.wikistarwars.R
import com.arthurgonzaga.wikistarwars.data.model.CharacterEntity
import com.arthurgonzaga.wikistarwars.databinding.FragmentDetailBinding
import com.arthurgonzaga.wikistarwars.databinding.FragmentHomeBinding
import com.arthurgonzaga.wikistarwars.ui.components.CharacterInfo
import com.arthurgonzaga.wikistarwars.util.setImage
import com.arthurgonzaga.wikistarwars.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


/**
 *
 *  This fragment will display full information about a character
 *
 */
@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    private val vm: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater)

        binding.character = args.character

        val characterId = args.character.id
        binding.favoriteButton.setOnClickListener {
            if (args.character.isFavorite) {
                vm.unFavoriteCharacter(characterId)
                binding.favoriteButton.setImage(false)
                args.character.isFavorite = false
            } else {
                vm.favoriteCharacter(characterId)
                binding.favoriteButton.setImage(true)
                args.character.isFavorite = true
            }
        }

        setupAnimations()

        return binding.root
    }


    fun setupAnimations() {
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation

        lifecycleScope.launch {
            showInfosWithAnimation()
        }
    }

    private suspend fun showInfosWithAnimation() {
        delay(260)
        binding.gridLayout.forEach { info ->
            (info as CharacterInfo)
            info.show()
            delay(25)
        }
    }
}