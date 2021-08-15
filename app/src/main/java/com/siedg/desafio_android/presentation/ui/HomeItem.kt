package com.siedg.desafio_android.presentation.ui

import com.siedg.desafio_android.R
import com.siedg.desafio_android.data.model.PersonModel
import com.siedg.desafio_android.databinding.HomeItemBinding
import com.xwray.groupie.databinding.BindableItem

class HomeItem(private val personModel: PersonModel): BindableItem<HomeItemBinding>() {
    override fun bind(viewBinding: HomeItemBinding, position: Int) {
        viewBinding.apply {
            tvHomeItemName.setText(personModel.name)
            tvHomeItemGender.setText(personModel.gender)
            tvHomeItemHeight.setText(personModel.height.toString())
            tvHomeItemMass.setText(personModel.mass.toString())
        }
    }

    override fun getLayout(): Int = R.layout.home_item
}