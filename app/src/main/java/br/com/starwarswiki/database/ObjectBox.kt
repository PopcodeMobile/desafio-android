package br.com.starwarswiki.database

import android.content.Context
import br.com.starwarswiki.models.MyObjectBox
import io.objectbox.BoxStore

object ObjectBox {
    var boxStore: BoxStore? = null
        private set

    fun build(context: Context) {
        boxStore = MyObjectBox.builder().androidContext(context.applicationContext).build()
    }
}