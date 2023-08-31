package com.markadamson83.powerampsubsonicprovider.domain.server

class InMemoryServerStore(private val servers: MutableList<Server> = mutableListOf()) {
    fun createServer(
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

    private fun saveServer(server: Server) {
        servers.add(server)
    }

    private fun createServerIdFor(serverName: String): String {
        return serverName.filterNot { it.isWhitespace() } + "Id"
    }
}