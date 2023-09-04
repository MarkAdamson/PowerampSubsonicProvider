package com.markadamson83.powerampsubsonicprovider.app

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TestDispatchers : CoroutineDispatchers {
    override val background: CoroutineDispatcher = Dispatchers.Unconfined
}