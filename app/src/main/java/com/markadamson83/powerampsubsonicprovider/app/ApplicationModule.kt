package com.markadamson83.powerampsubsonicprovider.app

import androidx.room.Room
import com.markadamson83.powerampsubsonicprovider.addserver.AddServerViewModel
import com.markadamson83.powerampsubsonicprovider.domain.server.RoomDbServerStore
import com.markadamson83.powerampsubsonicprovider.domain.server.ServerRepository
import com.markadamson83.powerampsubsonicprovider.domain.server.ServerStore
import com.markadamson83.powerampsubsonicprovider.domain.validation.ServerValidator
import com.markadamson83.powerampsubsonicprovider.roomdb.PSPDatabase
import com.markadamson83.powerampsubsonicprovider.servers.ServersViewModel
import com.markadamson83.powerampsubsonicprovider.subsonic.SubsonicServerValidator
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module {
    single<CoroutineDispatchers> { DefaultDispatchers() }
    single<ServerStore> { RoomDbServerStore(
        db = Room.databaseBuilder(
            androidContext(),
            PSPDatabase::class.java,
            "psp-db"
        ).build()
    ) }
    factory<ServerValidator> { SubsonicServerValidator() }
    factory { ServerRepository(serverStore = get())}

    viewModel { AddServerViewModel(
        serverValidator = get(),
        serverRepository = get(),
        dispatchers = get()
    ) }

    viewModel { ServersViewModel(
        serverStore = get(),
        dispatchers = get()
    ) }
}