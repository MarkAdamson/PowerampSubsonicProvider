package com.markadamson83.powerampsubsonicprovider.servers

import com.markadamson83.powerampsubsonicprovider.InstantTaskExecutorExtension
import com.markadamson83.powerampsubsonicprovider.servers.state.ServersState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class ServersTest {

    @Test
    fun noServersConfigured() {
        val viewModel = ServersViewModel()

        viewModel.getServers()

        assertEquals(ServersState.Servers(emptyList()), viewModel.serversState.value)
    }
}