package com.markadamson83.powerampsubsonicprovider.domain.server

interface ServerStore {

    fun servers(): List<Server>

    suspend fun createServer(
        serverName: String,
        baseURL: String,
        username: String,
        password: String
    ): Server

    fun deleteServer(serverId: String)
}