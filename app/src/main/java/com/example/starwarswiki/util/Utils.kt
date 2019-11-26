package com.example.starwarswiki.util

import android.content.res.Resources
import com.example.starwarswiki.R
import com.example.starwarswiki.domain.PersonModel

fun formatShortDetails(item: PersonModel, res: Resources): String{
    return res.getString(R.string.person_characteristics, item.height, item.gender, item.mass)
}

fun formatLongDetails(item: PersonModel, res:Resources): String{
    return res.getString(R.string.person_detailed, item.height, item.gender, item.mass, item.hair_color, item.skin_color, item.eye_color, item.birth_year)
}