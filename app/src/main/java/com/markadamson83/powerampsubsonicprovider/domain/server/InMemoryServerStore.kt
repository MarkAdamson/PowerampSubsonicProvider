package com.markadamson83.powerampsubsonicprovider.domain.server

class InMemoryServerStore(private val servers: MutableList<Server> = mutableListOf()) {
    @Throws(UnresponsiveServerException::class)
    fun createServer(
        serverName: String,
        baseURL: String,
        username: String,
        password: String
    ): Server {
        if (baseURL == "http://bad.demo.subsonic.org") {
            throw UnresponsiveServerException()
        }
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

    class UnresponsiveServerException : Exception()
}