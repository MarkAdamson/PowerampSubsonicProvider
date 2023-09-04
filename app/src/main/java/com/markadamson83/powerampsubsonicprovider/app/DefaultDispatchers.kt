package com.markadamson83.powerampsubsonicprovider.app

import kotlinx.coroutines.Dispatchers

class DefaultDispatchers : CoroutineDispatchers {
    override val background = Dispatchers.IO
}
