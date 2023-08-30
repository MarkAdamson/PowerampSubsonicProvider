package com.markadamson83.powerampsubsonicprovider.addserver

import com.markadamson83.powerampsubsonicprovider.InstantTaskExecutorExtension
import com.markadamson83.powerampsubsonicprovider.addserver.state.AddServerState
import com.markadamson83.powerampsubsonicprovider.domain.server.Server
import com.markadamson83.powerampsubsonicprovider.domain.validation.ServerValidator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class AddServerTest {

    @Test
    fun serverAdded() {
        val demoServer = Server("DemoServerId", "Demo Server", "http://demo.subsonic.org", "guest1", "guest")
        val viewModel = AddServerViewModel(ServerValidator())

        viewModel.addServer(demoServer.serverName, demoServer.baseURL, demoServer.username, demoServer.password)

        Assertions.assertEquals(AddServerState.ServerExists(demoServer), viewModel.addServerState.value)
    }

    @Test
    fun anotherServerAdded() {
        val demoServer = Server("DemoServer2Id", "Demo Server 2", "http://demo.subsonic.org", "guest2", "guest")
        val viewModel = AddServerViewModel(ServerValidator())

        viewModel.addServer(demoServer.serverName, demoServer.baseURL, demoServer.username, demoServer.password)

        Assertions.assertEquals(AddServerState.ServerExists(demoServer), viewModel.addServerState.value)
    }
}