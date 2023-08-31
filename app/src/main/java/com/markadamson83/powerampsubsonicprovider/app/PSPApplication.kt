package com.markadamson83.powerampsubsonicprovider.app

import android.app.Application
import org.koin.core.context.GlobalContext.startKoin

class PSPApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(applicationModule)
        }
    }
}