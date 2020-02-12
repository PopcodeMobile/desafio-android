package br.albuquerque.core.network

import android.content.Context
import androidx.annotation.StringRes

class StringWrapper {

    private var messageRes: Int? = null
    private var messageStr: String? = null

    constructor(@StringRes message: Int) {
        messageRes = message
    }

    constructor(message: String) {
        messageStr = message
    }

    operator fun invoke(context: Context) = messageStr ?: run { messageRes?.let { context.getString(it) } ?: run { "" } }

}

fun String.toStringWrapper() = StringWrapper(this)