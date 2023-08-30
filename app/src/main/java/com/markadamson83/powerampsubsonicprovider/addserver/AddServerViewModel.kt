package com.markadamson83.powerampsubsonicprovider.addserver

import androidx.core.util.PatternsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.markadamson83.powerampsubsonicprovider.addserver.state.AddServerState

class AddServerViewModel {
    private val _mutableAddServerState = MutableLiveData<AddServerState>()
    val addServerState: LiveData<AddServerState> = _mutableAddServerState

    fun addServer(baseURL: String, username: String, password: String) {
        if (!PatternsCompat.WEB_URL.matcher(baseURL).matches()) {
            _mutableAddServerState.value = AddServerState.InvalidURL
        } else if (username.isEmpty()) {
            _mutableAddServerState.value = AddServerState.InvalidUsername
        } else if (password.isEmpty()) {
            _mutableAddServerState.value = AddServerState.InvalidPassword
        }
    }
}