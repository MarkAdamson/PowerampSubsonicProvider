package com.markadamson83.powerampsubsonicprovider.domain.server

data class Server(
    val serverId: String,
    val serverName: String,
    val baseURL: String,
    val username: String,
    val password: String
) {

}
