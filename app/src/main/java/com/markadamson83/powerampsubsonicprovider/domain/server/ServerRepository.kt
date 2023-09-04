package com.markadamson83.powerampsubsonicprovider.domain.server

import com.markadamson83.powerampsubsonicprovider.addserver.state.AddServerState
import com.markadamson83.powerampsubsonicprovider.domain.exceptions.BackendException
import com.markadamson83.powerampsubsonicprovider.domain.exceptions.ConnectionUnavailableException
import com.markadamson83.powerampsubsonicprovider.domain.exceptions.UnresponsiveServerException

class ServerRepository(private val serverStore: ServerStore) {
    suspend fun createAndAddServer(
        serverName: String,
        baseURL: String,
        username: String,
        password: String
    ): AddServerState {
        return try {
            val server = serverStore.createServer(
                serverName,
                baseURL,
                username,
                password
            )
            AddServerState.ServerExists(server)
        } catch (e: UnresponsiveServerException) {
            AddServerState.UnresponsiveServer
        } catch (e: BackendException) {
            AddServerState.BackendError
        } catch (e: ConnectionUnavailableException) {
            AddServerState.Offline
        }
    }
}