package com.markadamson83.powerampsubsonicprovider.addserver

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markadamson83.powerampsubsonicprovider.addserver.state.AddServerState
import com.markadamson83.powerampsubsonicprovider.app.CoroutineDispatchers
import com.markadamson83.powerampsubsonicprovider.domain.server.ServerRepository
import com.markadamson83.powerampsubsonicprovider.domain.validation.ServerValidationResult
import com.markadamson83.powerampsubsonicprovider.domain.validation.ServerValidator
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddServerViewModel(private val serverValidator: ServerValidator,
                         private val serverRepository: ServerRepository,
                         private val dispatchers: CoroutineDispatchers
) : ViewModel() {
    private val mutableAddServerState = MutableLiveData<AddServerState>()
    val addServerState: LiveData<AddServerState> = mutableAddServerState

    fun addServer(serverName: String, baseURL: String, username: String, password: String) {
        viewModelScope.launch {
            mutableAddServerState.value = AddServerState.Saving

            mutableAddServerState.value = withContext(dispatchers.background) {
                when (serverValidator.validate(serverName, baseURL, username, password)) {
                    is ServerValidationResult.InvalidServerName ->
                        AddServerState.BadServerName
                    is ServerValidationResult.InvalidURL ->
                        AddServerState.BadURL
                    is ServerValidationResult.InvalidUsername ->
                        AddServerState.BadUsername
                    is ServerValidationResult.InvalidPassword ->
                        AddServerState.BadPassword
                    is ServerValidationResult.InvalidCredentials ->
                        AddServerState.BadCredentials
                    is ServerValidationResult.BackendError ->
                        AddServerState.BackendError
                    is ServerValidationResult.Timeout ->
                        AddServerState.UnresponsiveServer
                    is ServerValidationResult.Valid ->
                        performAddServer(serverName, baseURL, username, password)
                }
            }

        }
    }

    private suspend fun performAddServer(
        serverName: String,
        baseURL: String,
        username: String,
        password: String
    ) : AddServerState {
            return serverRepository.createAndAddServer(serverName, baseURL, username, password)
    }

    fun resetState() {
        mutableAddServerState.value = null
    }
}