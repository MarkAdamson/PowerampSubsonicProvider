package com.markadamson83.powerampsubsonicprovider.addserver.state

sealed class AddServerState {
    object BadServerName : AddServerState()
    object BadURL : AddServerState()
    object BadUsername : AddServerState()
    object BadPassword : AddServerState()
}
