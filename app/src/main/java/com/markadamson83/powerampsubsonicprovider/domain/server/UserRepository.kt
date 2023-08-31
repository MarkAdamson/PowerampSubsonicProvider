package com.markadamson83.powerampsubsonicprovider.domain.server

import com.markadamson83.powerampsubsonicprovider.addserver.state.AddServerState

class UserRepository(private val serverStore: InMemoryServerStore) {
    fun createAndAddServer(
        serverName: String,
        baseURL: String,
        username: String,
        password: String
    ): AddServerState {
        val server = serverStore.createServer(
            serverName,
            baseURL,
            username,
            password
        )
        return AddServerState.ServerExists(server)
    }
}