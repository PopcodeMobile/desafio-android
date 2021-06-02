package com.knowledge.wikisw_luan.adapter

import com.knowledge.wikisw_luan.models.Character

interface ClickWikiListener {

    fun onListClick(character: Character)

    fun onFavClick(character: Character)

}