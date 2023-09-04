package com.markadamson83.powerampsubsonicprovider.servers

import com.markadamson83.powerampsubsonicprovider.InstantTaskExecutorExtension
import com.markadamson83.powerampsubsonicprovider.domain.server.Server
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

    @Test
    fun serversConfigured() {
        val demoServer = Server("DemoServerId", "Demo Server", "http://demo.subsonic.org", "guest1", "guest")
        val demoServer2 = Server("DemoServer2Id", "Demo Server 2", "http://demo.subsonic.org", "guest2", "guest")
        val viewModel = ServersViewModel(listOf(demoServer, demoServer2))

        viewModel.getServers()

        assertEquals(ServersState.Servers(listOf(demoServer, demoServer2)), viewModel.serversState.value)
    }
}