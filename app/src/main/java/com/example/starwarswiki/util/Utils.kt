package com.example.starwarswiki.util

import android.content.res.Resources
import com.example.starwarswiki.R
import com.example.starwarswiki.domain.PersonModel

fun formatShortDetails(item: PersonModel, res: Resources): String{
    return res.getString(R.string.person_characteristics, item.height, item.gender, item.mass)
}