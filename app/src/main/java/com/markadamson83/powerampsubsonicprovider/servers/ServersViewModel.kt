package com.markadamson83.powerampsubsonicprovider.servers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.markadamson83.powerampsubsonicprovider.domain.server.InMemoryServerStore
import com.markadamson83.powerampsubsonicprovider.domain.server.ServerStore
import com.markadamson83.powerampsubsonicprovider.servers.state.ServersState

class ServersViewModel(
    private val serverStore: ServerStore = InMemoryServerStore()
) {

    private val mutableServersState = MutableLiveData<ServersState>()
    val serversState: LiveData<ServersState> = mutableServersState

    fun getServers() {
        mutableServersState.value = ServersState.Servers(serverStore.servers())
    }
}
