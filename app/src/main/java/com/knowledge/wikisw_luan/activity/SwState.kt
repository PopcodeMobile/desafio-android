package com.knowledge.wikisw_luan.activity

import com.knowledge.wikisw_luan.models.CharacterModel

sealed class SwState {
    class ShowCharacters(val list: List<CharacterModel>) : SwState()
}
