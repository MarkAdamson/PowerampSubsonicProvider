package com.markadamson83.powerampsubsonicprovider.addserver.state

import com.markadamson83.powerampsubsonicprovider.domain.server.Server

sealed class AddServerState {
    data class ServerExists(val server: Server) : AddServerState()
    object BadServerName : AddServerState()
    object BadURL : AddServerState()
    object BadUsername : AddServerState()
    object BadPassword : AddServerState()
    object BadCredentials: AddServerState()
    object UnresponsiveServer : AddServerState()
    object BackendError : AddServerState()
    object Offline : AddServerState()
    object Saving: AddServerState()
}
