package com.albuquerque.starwarswiki.app.view.handler

import br.albuquerque.data.ui.PersonUI

interface PersonHandler {

    fun handleFavorite(person: PersonUI, position: Int)

    fun handleClick(person: PersonUI)

}