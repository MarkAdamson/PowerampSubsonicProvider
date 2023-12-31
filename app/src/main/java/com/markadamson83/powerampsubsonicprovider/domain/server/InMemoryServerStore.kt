package com.markadamson83.powerampsubsonicprovider.domain.server

import com.markadamson83.powerampsubsonicprovider.domain.exceptions.UnresponsiveServerException

class InMemoryServerStore(private var servers: List<Server> = listOf()) :
    ServerStore {
    override suspend fun servers(): List<Server> {
        return servers
    }

    @Throws(UnresponsiveServerException::class)
    override suspend fun createServer(
        serverName: String,
        baseURL: String,
        username: String,
        password: String
    ): Server {
        val serverId = createServerIdFor(serverName)
        val server = Server(
            serverId,
            serverName,
            baseURL,
            username,
            password
        )
        saveServer(server)
        return server
    }

    override suspend fun deleteServer(serverId: String) {
        servers = servers.filterNot { it.serverId == serverId }
    }

    private fun createServerIdFor(serverName: String): String {
        return serverName.filterNot { it.isWhitespace() } + "Id"
    }

    private fun saveServer(server: Server) {
        servers = servers + server
    }

}