package com.markadamson83.powerampsubsonicprovider.addserver.state

import com.markadamson83.powerampsubsonicprovider.domain.server.Server

sealed class AddServerState {
    data class ServerExists(val server: Server) : AddServerState()
    object BadServerName : AddServerState()
    object BadURL : AddServerState()
    object BadUsername : AddServerState()
    object BadPassword : AddServerState()
    object UnresponsiveServer : AddServerState()
}
