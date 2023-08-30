package com.markadamson83.powerampsubsonicprovider.addserver

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.markadamson83.powerampsubsonicprovider.addserver.state.AddServerState

class AddServerViewModel {
    private val _mutableAddServerState = MutableLiveData<AddServerState>()
    val addServerState: LiveData<AddServerState> = _mutableAddServerState

    fun addServer(baseURL: String, username: String, password: String) {
        _mutableAddServerState.value = AddServerState.InvalidURL
    }
}