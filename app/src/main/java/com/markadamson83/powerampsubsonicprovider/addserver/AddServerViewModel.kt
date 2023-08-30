package com.markadamson83.powerampsubsonicprovider.addserver

import androidx.core.util.PatternsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.markadamson83.powerampsubsonicprovider.addserver.state.AddServerState

class AddServerViewModel {
    private val _mutableAddServerState = MutableLiveData<AddServerState>()
    val addServerState: LiveData<AddServerState> = _mutableAddServerState

    fun addServer(baseURL: String, username: String, password: String) {
        _mutableAddServerState.value = when (ServerValidator().validate(baseURL, username, password)) {
            is ServerValidationResult.InvalidURL ->
                AddServerState.BadURL
            is ServerValidationResult.InvalidUsername ->
                AddServerState.BadUsername
            is ServerValidationResult.InvalidPassword ->
                AddServerState.BadPassword
        }
    }

    class ServerValidator {
        fun validate(
            baseURL: String,
            username: String,
            password: String
        ): ServerValidationResult {
            val result = if (!PatternsCompat.WEB_URL.matcher(baseURL).matches()) {
                ServerValidationResult.InvalidURL
            } else if (username.isEmpty()) {
                ServerValidationResult.InvalidUsername
            } else if (password.isEmpty()) {
                ServerValidationResult.InvalidPassword
            } else TODO()
            return result
        }
    }

    sealed class ServerValidationResult {
        object InvalidURL : ServerValidationResult()
        object InvalidUsername : ServerValidationResult()
        object InvalidPassword : ServerValidationResult()
    }
}