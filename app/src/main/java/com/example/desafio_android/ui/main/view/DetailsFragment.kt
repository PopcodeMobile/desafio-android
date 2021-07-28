package com.example.desafio_android.ui.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.desafio_android.R
import com.example.desafio_android.data.model.People
import com.example.desafio_android.databinding.FragmentDetailsBinding
import com.example.desafio_android.ui.main.viewmodel.DetailsViewModel
import com.example.desafio_android.util.Resultado
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    val detailsViewModel: DetailsViewModel by viewModels()

    private val args by navArgs<DetailsFragmentArgs>()

    lateinit var _bindingDetails: FragmentDetailsBinding
    val bindingDetails: FragmentDetailsBinding get() = _bindingDetails

    var isToggleChecked = false

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _bindingDetails = FragmentDetailsBinding.inflate(inflater, container, false)

        val people = args.people

        actionToggleButton(people)
        verifyPeople(people)

        getNomePlaneta(people)
        getNomeEspecie(people)
        setupTextView(people)

        return bindingDetails.root

    }

    private fun verifyPeople(people: People) {
        lifecycleScope.launch {
            val countFavorite = detailsViewModel.checkPeople(people)
            if (countFavorite > 0) {
                bindingDetails.toggleFavorite.isChecked = true
                isToggleChecked = true
            } else {
                bindingDetails.toggleFavorite.isChecked = false
                isToggleChecked = false
            }
        }
    }

    private fun actionToggleButton(people: People) {
        bindingDetails.toggleFavorite.setOnClickListener {
            isToggleChecked = !isToggleChecked
            if (isToggleChecked) {
                detailsViewModel.addFavorite(people)
            } else {
                detailsViewModel.removeFromFavorite(people)
            }
            bindingDetails.toggleFavorite.isChecked = isToggleChecked
        }
    }

    @SuppressLint("SetTextI18n")
    fun getNomePlaneta(people: People) {
        bindingDetails.apply {
            lifecycleScope.launch {
                detailsViewModel.getNamePlanet(people.homeworld)
                    .observe(viewLifecycleOwner) { resposta ->
                        when (resposta) {
                            is Resultado.Sucesso -> {
                                nomeDoPlanetaNatalDetails.text = nomeDoPlanetaNatalDetails.context
                                    .getString(R.string.PlanetaNatal) + " " + resposta.dado!!.name
                            }
                            is Resultado.Erro -> {
                                nomeDoPlanetaNatalDetails.text =
                                    resposta.exception.message.toString()
                            }
                        }
                    }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getNomeEspecie(people: People) {
        bindingDetails.apply {
            lifecycleScope.launch {
                if (!people.species.isEmpty()) {
                    detailsViewModel.getNameSpecie(people.species.get(0))
                        .observe(viewLifecycleOwner) { resposta ->
                            when (resposta) {
                                is Resultado.Sucesso -> {
                                    nomeDaEspecieDetails.text = nomeDaEspecieDetails.context
                                        .getString(R.string.NomeSpecie) + " " + resposta.dado!!.name
                                }
                                is Resultado.Erro -> {
                                    nomeDaEspecieDetails.text =
                                        resposta.exception.message.toString()
                                }
                            }
                        }
                } else {
                    bindingDetails.nomeDaEspecieDetails.text = nomeDaEspecieDetails.context
                        .getString(R.string.NomeSpecie) + " " + getString(R.string.SemInformacao)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun setupTextView(people: People) {
        bindingDetails.apply {
            nomePersonagemDetails.text = people.name
            alturaPersonagemDetails.text =
                alturaPersonagemDetails.context.getString(R.string.Altura) + " " +
                        people.height + " " + alturaPersonagemDetails.context.getString(R.string.cm)

            pesoPersonagemDetails.text =
                pesoPersonagemDetails.context.getString(R.string.Peso) + " " + people.mass + " " +
                        pesoPersonagemDetails.context.getString(
                            R.string.kg
                        )

            hairColorDetails.text =
                hairColorDetails.context.getString(R.string.CorDoCabelo) + " " + people.hairColor

            skinColorDetails.text =
                skinColorDetails.context.getString(R.string.CorDaPele) + " " + people.skinColor

            eyeColorDetails.text =
                eyeColorDetails.context.getString(R.string.CorDosOlhos) + " " + people.eyeColor

            birthYearDetails.text =
                birthYearDetails.context.getString(R.string.AnoDeNascimento) + " " + people.birthYear

            genderPersonagemDetails.text =
                genderPersonagemDetails.context.getString(R.string.Genero) + " " + people.gender

        }

    }

}