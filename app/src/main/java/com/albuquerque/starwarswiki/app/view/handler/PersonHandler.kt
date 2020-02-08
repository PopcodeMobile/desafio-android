package com.albuquerque.starwarswiki.app.view.handler

import com.albuquerque.starwarswiki.app.model.ui.PersonUI

interface PersonHandler {

    fun handleFavorite(person: PersonUI, position: Int)

}