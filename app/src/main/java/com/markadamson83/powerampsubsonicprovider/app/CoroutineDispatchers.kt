package com.markadamson83.powerampsubsonicprovider.app

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatchers {
    val background: CoroutineDispatcher
}