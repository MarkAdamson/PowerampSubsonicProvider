package com.markadamson83.powerampsubsonicprovider.servers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.markadamson83.powerampsubsonicprovider.domain.server.InMemoryServerStore
import com.markadamson83.powerampsubsonicprovider.domain.server.ServerStore
import com.markadamson83.powerampsubsonicprovider.servers.state.ServersState

class ServersViewModel(
    private val serverStore: ServerStore = InMemoryServerStore()
) : ViewModel() {

    private val mutableServersState = MutableLiveData<ServersState>()
    val serversState: LiveData<ServersState> = mutableServersState

    fun getServers() {
        mutableServersState.value = ServersState.Servers(serverStore.servers())
    }

    fun deleteServer(serverId: String) {
        serverStore.deleteServer(serverId)
        mutableServersState.value = ServersState.Servers(serverStore.servers())
    }
}
