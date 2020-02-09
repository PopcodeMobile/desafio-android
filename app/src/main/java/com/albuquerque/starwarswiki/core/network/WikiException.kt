package com.albuquerque.starwarswiki.core.network

open class WikiException @JvmOverloads constructor(
    var errorMessage: StringWrapper? = null,
    message: String? = null,
    cause: Throwable? = null,
    var code: Int? = null
) : Exception(message, cause)