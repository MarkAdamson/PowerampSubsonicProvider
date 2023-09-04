package com.markadamson83.powerampsubsonicprovider.addserver

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.markadamson83.powerampsubsonicprovider.addserver.state.AddServerState
import com.markadamson83.powerampsubsonicprovider.domain.server.ServerRepository
import com.markadamson83.powerampsubsonicprovider.domain.validation.ServerValidationResult
import com.markadamson83.powerampsubsonicprovider.domain.validation.ServerValidator

class AddServerViewModel(private val serverValidator: ServerValidator,
                         private val serverRepository: ServerRepository
) : ViewModel() {
    private val _mutableAddServerState = MutableLiveData<AddServerState>()
    val addServerState: LiveData<AddServerState> = _mutableAddServerState

    fun addServer(serverName: String, baseURL: String, username: String, password: String) {
        when (serverValidator.validate(serverName, baseURL, username, password)) {
            is ServerValidationResult.InvalidServerName ->
                _mutableAddServerState.value = AddServerState.BadServerName
            is ServerValidationResult.InvalidURL ->
                _mutableAddServerState.value = AddServerState.BadURL
            is ServerValidationResult.InvalidUsername ->
                _mutableAddServerState.value = AddServerState.BadUsername
            is ServerValidationResult.InvalidPassword ->
                _mutableAddServerState.value = AddServerState.BadPassword
            is ServerValidationResult.Valid ->
                performAddServer(serverName, baseURL, username, password)
        }
    }

    private fun performAddServer(
        serverName: String,
        baseURL: String,
        username: String,
        password: String
    ) {
        _mutableAddServerState.value = AddServerState.Saving
        _mutableAddServerState.value = serverRepository.createAndAddServer(serverName, baseURL, username, password)
    }

}