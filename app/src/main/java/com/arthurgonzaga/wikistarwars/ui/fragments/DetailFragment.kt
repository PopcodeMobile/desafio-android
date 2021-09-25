package com.arthurgonzaga.wikistarwars.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.arthurgonzaga.wikistarwars.R
import com.arthurgonzaga.wikistarwars.databinding.FragmentDetailBinding
import com.arthurgonzaga.wikistarwars.databinding.FragmentHomeBinding
import com.arthurgonzaga.wikistarwars.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 *
 *  This fragment will display full information about a character
 *
 */
@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    private val vm: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailBinding.inflate(inflater)


        return binding.root
    }
}