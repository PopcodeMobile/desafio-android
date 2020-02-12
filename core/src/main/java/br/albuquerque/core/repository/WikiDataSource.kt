package br.albuquerque.core.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class WikiDataSource: CoroutineScope by CoroutineScope(Dispatchers.IO)