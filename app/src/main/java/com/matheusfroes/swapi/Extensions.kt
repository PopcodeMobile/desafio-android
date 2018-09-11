package com.matheusfroes.swapi

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.net.Uri
import android.support.v4.app.FragmentActivity
import android.widget.Toast
import com.matheusfroes.swapi.di.Injector
import kotlinx.coroutines.experimental.async
import kotlin.coroutines.experimental.CoroutineContext

val Activity.app: SwapiApplication get() = application as SwapiApplication
val Activity.appInjector: Injector get() = app.injector

typealias NextPageUrl = String?

fun extractIdFromUrl(url: String): Long {
    val uri = Uri.parse(url)
    val id = uri.lastPathSegment ?: "0"
    return id.toLong()
}

fun Activity.toast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/**
 * For Activities, allows declarations like
 * ```
 * val myViewModel = viewModelProvider(myViewModelFactory)
 * ```
 */
inline fun <reified VM : ViewModel> FragmentActivity.viewModelProvider(
        provider: ViewModelProvider.Factory
) =
        ViewModelProviders.of(this, provider).get(VM::class.java)


suspend fun <A, B> List<A>.parallelMap(
        context: CoroutineContext = networkContext,
        block: suspend (A) -> B
): List<B> {
    return map {
        // Use async to start a coroutine for each item
        async(context) {
            block(it)
        }
    }.map {
        // We now have a map of Deferred<T> so we await() each
        it.await()
    }
}