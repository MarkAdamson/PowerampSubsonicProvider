package com.markadamson83.powerampsubsonicprovider.app

import com.markadamson83.powerampsubsonicprovider.addserver.AddServerViewModel
import com.markadamson83.powerampsubsonicprovider.domain.server.InMemoryServerStore
import com.markadamson83.powerampsubsonicprovider.domain.server.ServerRepository
import com.markadamson83.powerampsubsonicprovider.domain.validation.ServerValidator
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module {
    single { InMemoryServerStore() }
    factory { ServerValidator() }
    factory { ServerRepository(serverStore = get())}

    viewModel { AddServerViewModel(
        serverValidator = get(),
        serverRepository = get()
    ) }
}