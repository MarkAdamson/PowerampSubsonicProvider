package com.markadamson83.powerampsubsonicprovider.domain.server

interface ServerStore {

    fun createServer(
        serverName: String,
        baseURL: String,
        username: String,
        password: String
    ): Server
}