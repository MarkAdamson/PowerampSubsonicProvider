package com.markadamson83.powerampsubsonicprovider.domain.server

interface ServerStore {

    suspend fun servers(): List<Server>

    suspend fun createServer(
        serverName: String,
        baseURL: String,
        username: String,
        password: String
    ): Server

    suspend fun deleteServer(serverId: String)
}