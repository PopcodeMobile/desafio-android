package br.com.starwarswiki.views.person


import android.content.Context
import br.com.starwarswiki.anvil.FrameLayoutComponent
import br.com.starwarswiki.models.Person

abstract class BasePeopleView(context: Context): FrameLayoutComponent(context) {

    protected var person: Person? = null

    fun person(person: Person) {
        this.person = person
        render()
    }
}