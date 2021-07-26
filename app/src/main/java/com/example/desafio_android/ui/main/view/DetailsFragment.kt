package com.example.desafio_android.ui.main.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.desafio_android.R
import com.example.desafio_android.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {

    private val args by navArgs<DetailsFragmentArgs>()

    lateinit var _bindingDetails: FragmentDetailsBinding
    val bindingDetails: FragmentDetailsBinding get() = _bindingDetails

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _bindingDetails = FragmentDetailsBinding.inflate(inflater, container, false)

        val people = args.people
        bindingDetails.apply {
            nomePersonagemDetails.text = people.name
            alturaPersonagemDetails.text = alturaPersonagemDetails.context.
            getString(R.string.Altura) + " " + people.height + " " + alturaPersonagemDetails.context.
            getString(R.string.cm)

            pesoPersonagemDetails.text = pesoPersonagemDetails.context.
            getString(R.string.Peso) + " " + people.mass + " " + pesoPersonagemDetails.context.
            getString(R.string.kg)

            hairColorDetails.text = hairColorDetails.context.
            getString(R.string.CorDoCabelo) + " " + people.hairColor

            skinColorDetails.text = skinColorDetails.context.
            getString(R.string.CorDaPele) + " " + people.skinColor

            eyeColorDetails.text = eyeColorDetails.context.
            getString(R.string.CorDosOlhos) + " " + people.eyeColor

            birthYearDetails.text =  birthYearDetails.context.
            getString(R.string.AnoDeNascimento) + " " + people.birthYear

            genderPersonagemDetails.text = genderPersonagemDetails.context.
            getString(R.string.Genero) + " " + people.gender
            //nomeDoPlanetaNatalDetails.text = people.homeworld
            //nomeDaEspecieDetails.text = people.species
        }

        return bindingDetails.root
    }
}