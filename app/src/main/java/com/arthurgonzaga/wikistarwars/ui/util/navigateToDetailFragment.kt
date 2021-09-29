package com.arthurgonzaga.wikistarwars.ui.util

import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.arthurgonzaga.wikistarwars.R
import com.arthurgonzaga.wikistarwars.data.model.CharacterEntity
import androidx.navigation.fragment.findNavController

fun Fragment.navigateToDetailFragment(
    characterEntity: CharacterEntity,
    textView: TextView,
    imageButton: ImageButton,
    viewGroup: ViewGroup,
    @IdRes actionId: Int
) {

    val extras = FragmentNavigatorExtras(
        textView to "heading_big",
        imageButton to "favorite_btn_big",
        viewGroup to "background"
    )

    val args = bundleOf("character" to characterEntity)
    findNavController().navigate(
        actionId,
        args,
        null,
        extras
    )
}