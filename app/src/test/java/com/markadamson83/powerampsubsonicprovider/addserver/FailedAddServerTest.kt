package com.markadamson83.powerampsubsonicprovider.addserver

import com.markadamson83.powerampsubsonicprovider.addserver.state.AddServerState
import com.markadamson83.powerampsubsonicprovider.domain.exceptions.BackendException
import com.markadamson83.powerampsubsonicprovider.domain.exceptions.ConnectionUnavailableException
import com.markadamson83.powerampsubsonicprovider.domain.exceptions.UnresponsiveServerException
import com.markadamson83.powerampsubsonicprovider.domain.server.Server
import com.markadamson83.powerampsubsonicprovider.domain.server.ServerRepository
import com.markadamson83.powerampsubsonicprovider.domain.server.ServerStore
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FailedAddServerTest {
    @Test
    fun backendError() = runBlocking {
        val serverRepository = ServerRepository(IncorrectUserOrPasswordServerStore())

        val result = serverRepository.createAndAddServer(":serverName:", ":baseURL:", ":username:", ":password:")

        assertEquals(AddServerState.BackendError, result)
    }

    @Test
    fun offlineError() = runBlocking {
        val serverRepository = ServerRepository(OfflineServerStore())

        val result = serverRepository.createAndAddServer(":serverName:", ":baseURL:", ":username:", ":password:")

        assertEquals(AddServerState.Offline, result)
    }

    @Test
    fun serverUnresponsiveError() = runBlocking {
        val serverRepository = ServerRepository(UnresponsiveServerStore())

        val result = serverRepository.createAndAddServer(":serverName:", ":baseURL:", ":username:", ":password:")

        assertEquals(AddServerState.UnresponsiveServer, result)
    }

    class IncorrectUserOrPasswordServerStore :
        ServerStore {
        override fun servers(): List<Server> {
            TODO("Not yet implemented")
        }

        override suspend fun createServer(
            serverName: String,
            baseURL: String,
            username: String,
            password: String
        ): Server {
            throw BackendException()
        }

        override fun deleteServer(serverId: String) {
            TODO("Not yet implemented")
        }
    }

    class OfflineServerStore :
        ServerStore {
        override fun servers(): List<Server> {
            TODO("Not yet implemented")
        }

        override suspend fun createServer(
            serverName: String,
            baseURL: String,
            username: String,
            password: String
        ): Server {
            throw ConnectionUnavailableException()
        }

        override fun deleteServer(serverId: String) {
            TODO("Not yet implemented")
        }
    }

    class UnresponsiveServerStore :
        ServerStore {
        override fun servers(): List<Server> {
            TODO("Not yet implemented")
        }

        override suspend fun createServer(
            serverName: String,
            baseURL: String,
            username: String,
            password: String
        ): Server {
            throw UnresponsiveServerException()
        }

        override fun deleteServer(serverId: String) {
            TODO("Not yet implemented")
        }
    }
}