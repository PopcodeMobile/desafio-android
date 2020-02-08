package com.albuquerque.starwarswiki.core.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class WikiRepository : CoroutineScope by CoroutineScope(Dispatchers.IO)