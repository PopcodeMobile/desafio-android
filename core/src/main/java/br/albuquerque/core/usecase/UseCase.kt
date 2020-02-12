package br.albuquerque.core.usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class ObservableUseCase: CoroutineScope by CoroutineScope(Dispatchers.IO) {

    open var onFailure:(Throwable) -> Unit = { }
}

abstract class PaginationUseCase : ObservableUseCase() {

    protected var _onPageSuccessfull: (Int) -> Unit = { }

    fun setOnPageSuccessfull(event: (Int) -> Unit) {
        _onPageSuccessfull = event
    }

}