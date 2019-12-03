package com.example.starwarswiki.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.starwarswiki.R
import com.example.starwarswiki.database.PersonRoomDatabase
import com.example.starwarswiki.databinding.PersonDetailFragmentBinding
import com.example.starwarswiki.viewmodel.PersonDetailViewModel
import com.example.starwarswiki.viewmodel.PersonDetailViewModelFactory
import timber.log.Timber
class PersonDetailFragment : Fragment() {
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = PersonDetailFragmentArgs.fromBundle(arguments!!)
        val binding = PersonDetailFragmentBinding.inflate(inflater)
        val application = requireNotNull(this.activity).application
        val dataSource = PersonRoomDatabase.getDatabase(application).personDao
        val viewModelFactory = PersonDetailViewModelFactory(args.id, dataSource)
        val personDetailViewModel =  ViewModelProviders
            .of(this, viewModelFactory).get(PersonDetailViewModel::class.java)
        val switch = binding.favoriteSwitch!!

        personDetailViewModel.person.observe(this, Observer {
            it?.let{
                switch.isChecked = it.isFavorite
            }
        })

        switch.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                switch.setThumbResource(R.drawable.ic_star)
                switch.setTrackResource(R.color.colorSecondary)
                personDetailViewModel.updateFavoriteStatus(true)
            }else{
                switch.setThumbResource(R.drawable.ic_star_border)
                switch.setTrackResource(R.color.colorPrimary)
                personDetailViewModel.updateFavoriteStatus(false)
            }
        }

        binding.personDetailViewModel = personDetailViewModel
        personDetailViewModel.planetName.observe(this, Observer {
            it?.let{
                val details = binding.detailsText.text
                binding.detailsText.text = "$details\nPlaneta natal: $it"
            }
        })
        personDetailViewModel.speciesName.observe(this, Observer{
            it?.let{
                var details = binding.detailsText.text.toString()
                details = "$details\nEspécie(s): "
                it.forEachIndexed { index, specie ->
                    if(index>0)
                        details += ", "
                    details += specie
                }
                binding.detailsText.text = details
            }
        })

        binding.lifecycleOwner = this
        return binding.root
    }
}
