package com.markadamson83.powerampsubsonicprovider.servers

import com.markadamson83.powerampsubsonicprovider.InstantTaskExecutorExtension
import com.markadamson83.powerampsubsonicprovider.app.TestDispatchers
import com.markadamson83.powerampsubsonicprovider.domain.server.InMemoryServerStore
import com.markadamson83.powerampsubsonicprovider.infrastructure.builder.ServerBuilder.Companion.aServer
import com.markadamson83.powerampsubsonicprovider.servers.state.ServersState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class ServersTest {

    private val viewModel = ServersViewModel(
        serverStore = InMemoryServerStore(),
        dispatchers = TestDispatchers()
    )

    @Test
    fun noServersConfigured() {
        viewModel.getServers()

        assertEquals(ServersState.Servers(emptyList()), viewModel.serversState.value)
    }

    @Test
    fun serversConfigured() {
        val demoServer = aServer().build()
        val demoServer2 = aServer().withName("Demo Server 2").build()
        val viewModel = ServersViewModel(
            InMemoryServerStore(
                mutableListOf(
                    demoServer,
                    demoServer2
                )
            ),
            TestDispatchers()
        )

        viewModel.getServers()

        assertEquals(ServersState.Servers(listOf(demoServer, demoServer2)), viewModel.serversState.value)
    }

    @Test
    fun deleteServer() {
        val demoServer = aServer().build()
        val demoServer2 = aServer().withName("Demo Server 2").build()
        val viewModel = ServersViewModel(
            InMemoryServerStore(
                mutableListOf(
                    demoServer,
                    demoServer2
                )
            ),
            TestDispatchers()
        )

        viewModel.deleteServer(demoServer.serverId)

        assertEquals(ServersState.Servers(listOf(demoServer2)), viewModel.serversState.value)
    }
}