package com.markadamson83.powerampsubsonicprovider.addserver

import com.markadamson83.powerampsubsonicprovider.addserver.state.AddServerState
import com.markadamson83.powerampsubsonicprovider.domain.exceptions.BackendException
import com.markadamson83.powerampsubsonicprovider.domain.exceptions.ConnectionUnavailableException
import com.markadamson83.powerampsubsonicprovider.domain.server.Server
import com.markadamson83.powerampsubsonicprovider.domain.server.ServerRepository
import com.markadamson83.powerampsubsonicprovider.domain.server.ServerStore
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FailedAddServerTest {
    @Test
    fun backendError() {
        val serverRepository = ServerRepository(IncorrectUserOrPasswordServerStore())

        val result = serverRepository.createAndAddServer(":serverName:", ":baseURL:", ":username:", ":password:")

        assertEquals(AddServerState.BackendError, result)
    }

    @Test
    fun offlineError() {
        val serverRepository = ServerRepository(OfflineServerStore())

        val result = serverRepository.createAndAddServer(":serverName:", ":baseURL:", ":username:", ":password:")

        assertEquals(AddServerState.Offline, result)
    }

    class IncorrectUserOrPasswordServerStore :
        ServerStore {
        override fun createServer(
            serverName: String,
            baseURL: String,
            username: String,
            password: String
        ): Server {
            throw BackendException()
        }
    }

    class OfflineServerStore :
        ServerStore {
        override fun createServer(
            serverName: String,
            baseURL: String,
            username: String,
            password: String
        ): Server {
            throw ConnectionUnavailableException()
        }
    }
}