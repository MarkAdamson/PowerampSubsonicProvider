package com.markadamson83.powerampsubsonicprovider.servers.state

import com.markadamson83.powerampsubsonicprovider.domain.server.Server

sealed class ServersState {
    data class Servers(val servers: List<Server>) : ServersState()
}
