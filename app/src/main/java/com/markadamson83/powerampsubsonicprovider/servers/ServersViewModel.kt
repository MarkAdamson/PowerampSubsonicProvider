package com.markadamson83.powerampsubsonicprovider.servers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markadamson83.powerampsubsonicprovider.app.CoroutineDispatchers
import com.markadamson83.powerampsubsonicprovider.domain.server.ServerStore
import com.markadamson83.powerampsubsonicprovider.servers.state.ServersState
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ServersViewModel(
    private val serverStore: ServerStore,
    private val dispatchers: CoroutineDispatchers
) : ViewModel() {

    private val mutableServersState = MutableLiveData<ServersState>()
    val serversState: LiveData<ServersState> = mutableServersState

    fun getServers() {
        viewModelScope.launch {
            mutableServersState.value = withContext(dispatchers.background) { ServersState.Servers(serverStore.servers()) }
        }
    }

    fun deleteServer(serverId: String) {
        viewModelScope.launch {
            mutableServersState.value = withContext(dispatchers.background) {
                serverStore.deleteServer(serverId)
                ServersState.Servers(serverStore.servers())
            }
        }
    }
}
