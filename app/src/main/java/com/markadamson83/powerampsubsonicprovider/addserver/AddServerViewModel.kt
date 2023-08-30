package com.markadamson83.powerampsubsonicprovider.addserver

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.markadamson83.powerampsubsonicprovider.addserver.state.AddServerState
import com.markadamson83.powerampsubsonicprovider.domain.validation.ServerValidationResult
import com.markadamson83.powerampsubsonicprovider.domain.validation.ServerValidator

class AddServerViewModel(private val serverValidator: ServerValidator) {
    private val _mutableAddServerState = MutableLiveData<AddServerState>()
    val addServerState: LiveData<AddServerState> = _mutableAddServerState

    fun addServer(baseURL: String, username: String, password: String) {
        _mutableAddServerState.value = when (serverValidator.validate(baseURL, username, password)) {
            is ServerValidationResult.InvalidURL ->
                AddServerState.BadURL
            is ServerValidationResult.InvalidUsername ->
                AddServerState.BadUsername
            is ServerValidationResult.InvalidPassword ->
                AddServerState.BadPassword
            else -> TODO()
        }
    }

}