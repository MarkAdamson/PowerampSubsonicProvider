package com.markadamson83.powerampsubsonicprovider.addserver

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.markadamson83.powerampsubsonicprovider.addserver.state.AddServerState
import com.markadamson83.powerampsubsonicprovider.domain.server.Server
import com.markadamson83.powerampsubsonicprovider.domain.validation.ServerValidationResult
import com.markadamson83.powerampsubsonicprovider.domain.validation.ServerValidator

class AddServerViewModel(private val serverValidator: ServerValidator) {
    private val _mutableAddServerState = MutableLiveData<AddServerState>()
    val addServerState: LiveData<AddServerState> = _mutableAddServerState

    fun addServer(serverName: String, baseURL: String, username: String, password: String) {
        _mutableAddServerState.value = when (serverValidator.validate(serverName, baseURL, username, password)) {
            is ServerValidationResult.InvalidServerName ->
                AddServerState.BadServerName
            is ServerValidationResult.InvalidURL ->
                AddServerState.BadURL
            is ServerValidationResult.InvalidUsername ->
                AddServerState.BadUsername
            is ServerValidationResult.InvalidPassword ->
                AddServerState.BadPassword
            is ServerValidationResult.Valid -> {
                val server = Server(
                    serverName,
                    baseURL,
                    username,
                    password
                )
                AddServerState.ServerExists(server)
            }
        }
    }

}