package com.markadamson83.powerampsubsonicprovider.addserver

import com.markadamson83.powerampsubsonicprovider.InstantTaskExecutorExtension
import com.markadamson83.powerampsubsonicprovider.addserver.state.AddServerState
import com.markadamson83.powerampsubsonicprovider.app.TestDispatchers
import com.markadamson83.powerampsubsonicprovider.domain.server.InMemoryServerStore
import com.markadamson83.powerampsubsonicprovider.domain.server.Server
import com.markadamson83.powerampsubsonicprovider.domain.server.ServerRepository
import com.markadamson83.powerampsubsonicprovider.domain.validation.ServerValidator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class ServerSavingTest {

    private val validator = ServerValidator()
    private val repository = ServerRepository(InMemoryServerStore())
    private val dispatchers = TestDispatchers()
    private val viewModel = AddServerViewModel(validator, repository, dispatchers)
    private val server = Server("DemoServerId", "Demo Server", "http://demo.subsonic.org", "guest1", "guest")

    @Test
    fun addServerStatesInParticularOrder() {
        val states = mutableListOf<AddServerState>()
        viewModel.addServerState.observeForever { states.add(it) }
        viewModel.addServer(server.serverName, server.baseURL, server.username, server.password)
        assertEquals(listOf(AddServerState.Saving, AddServerState.ServerExists(server)), states)
    }
}