package com.arthurgonzaga.wikistarwars.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arthurgonzaga.wikistarwars.R
import com.arthurgonzaga.wikistarwars.databinding.FragmentDetailBinding
import com.arthurgonzaga.wikistarwars.databinding.FragmentFavoriteListBinding
import com.arthurgonzaga.wikistarwars.databinding.FragmentHomeBinding


/**
 *
 *  This fragment will display the list of favorites characters
 *
 */
class FavoriteListFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFavoriteListBinding.inflate(inflater)


        return binding.root
    }
}