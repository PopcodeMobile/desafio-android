package br.com.starwarswiki.middlewares

import android.content.Context
import br.com.starwarswiki.models.AppState
import br.com.starwarswiki.utils.NetworkUtils
import com.github.raulccabreu.redukt.middlewares.BaseAnnotatedMiddleware

abstract class NetworkOnMiddleware(private val context: Context):
    BaseAnnotatedMiddleware<AppState>(){

    override fun canExecute(state: AppState): Boolean {
        return NetworkUtils.isOnline(context)
    }

}