package com.markadamson83.powerampsubsonicprovider.addserver.state

sealed class AddServerState {
    object InvalidURL : AddServerState()
}
