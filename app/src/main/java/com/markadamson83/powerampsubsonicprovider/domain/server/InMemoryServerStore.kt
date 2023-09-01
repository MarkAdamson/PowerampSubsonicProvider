package com.markadamson83.powerampsubsonicprovider.domain.server

import com.markadamson83.powerampsubsonicprovider.domain.exceptions.UnresponsiveServerException

class InMemoryServerStore(private val servers: MutableList<Server> = mutableListOf()) :
    ServerStore {
    @Throws(UnresponsiveServerException::class)
    override fun createServer(
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

    private fun createServerIdFor(serverName: String): String {
        return serverName.filterNot { it.isWhitespace() } + "Id"
    }

    private fun saveServer(server: Server) {
        servers.add(server)
    }

}