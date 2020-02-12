package br.albuquerque.core.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class WikiRepository : CoroutineScope by CoroutineScope(Dispatchers.IO)