package com.example.starwarswiki.ui

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.starwarswiki.R
import com.example.starwarswiki.database.PersonRoomDatabase
import com.example.starwarswiki.databinding.PersonDetailFragmentBinding
import com.example.starwarswiki.viewmodel.PersonDetailViewModel
import com.example.starwarswiki.viewmodel.PersonDetailViewModelFactory
import timber.log.Timber

const val KEY_CHECK = "check_key"
const val KEY_DETAILS = "details_key"
const val KEY_PLANET = "planet_key"
const val KEY_SPECIES = "species_key"
const val KEY_UPDATE = "update_key"

class PersonDetailFragment : Fragment() {
    private var checkSwitch = false
    private var savedDetails = ""
    private var planetName = ""
    private var speciesName = ""
    private var countRequest = 2
    private var noNeedUpdate = false

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
        binding.personDetailViewModel = personDetailViewModel
        val switch = binding.favoriteSwitch!!
        val fontName = Typeface.createFromAsset(context?.assets, "fonts/Starjout.ttf")
        val fontDetail = Typeface.createFromAsset(context?.assets, "fonts/Starjedi.ttf")
        binding.nameText.typeface = fontName
        binding.detailsText.typeface = fontDetail


        if(savedInstanceState != null){
            checkSwitch = savedInstanceState.getBoolean(KEY_CHECK, false)
            savedDetails = savedInstanceState.getString(KEY_DETAILS, "")
            planetName = savedInstanceState.getString(KEY_PLANET, "")
            speciesName = savedInstanceState.getString(KEY_SPECIES, "")
            noNeedUpdate = savedInstanceState.getBoolean(KEY_UPDATE, false)
        }
        binding.detailsText.addTextChangedListener(object: TextWatcher{
            var _ignore = false
            override fun afterTextChanged(s: Editable?) {
                s?.let{
                    if(_ignore)
                        return
                    _ignore = true
                    if((countRequest == 0) and (s.toString().isNotEmpty()) and (!noNeedUpdate)){
                        savedDetails = "$s"
                        Timber.d("afterTextChanged[1x]: $savedDetails")
                        binding.detailsText.text = savedDetails
                        noNeedUpdate = true
                    }
                    if(noNeedUpdate){
                        Timber.d("afterTextChanged: $savedDetails")
                        binding.detailsText.text = savedDetails
                    }
                    _ignore = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        personDetailViewModel.person.observe(this, Observer {
            it?.let{
                switch.isChecked = it.isFavorite
            }
        })

        switch.setOnCheckedChangeListener { _, isChecked ->
            checkSwitch = isChecked
            if(isChecked){
                switch.setThumbResource(R.drawable.ic_star)
                switch.setTrackResource(R.color.colorSecondary)
                personDetailViewModel.updateFavoriteStatus(true)
            }else{
                switch.setThumbResource(R.drawable.ic_star_border)
                switch.setTrackResource(R.color.colorAccent)
                personDetailViewModel.updateFavoriteStatus(false)
            }
        }


        personDetailViewModel.planetName.observe(this, Observer {
            it?.let{
                Timber.d("Planet name changed !")
                planetName = it
                var details = binding.detailsText.text.toString()
                details = "$details$it"
                countRequest--
                if(!noNeedUpdate)
                    binding.detailsText.text = details
            }
        })
        personDetailViewModel.speciesName.observe(this, Observer{
            it?.let{
                Timber.d("Specie changed !")
                speciesName = it
                var details = binding.detailsText.text.toString()
                details="$details$it"
                countRequest--
                if(!noNeedUpdate)
                    binding.detailsText.text = details
            }
        })

        binding.lifecycleOwner = this
        return binding.root
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_CHECK, checkSwitch)
        outState.putString(KEY_DETAILS, savedDetails)
        outState.putString(KEY_PLANET, planetName)
        outState.putString(KEY_SPECIES, speciesName)
        outState.putBoolean(KEY_UPDATE, noNeedUpdate)
    }
}
