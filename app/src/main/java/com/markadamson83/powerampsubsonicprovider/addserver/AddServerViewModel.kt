package com.markadamson83.powerampsubsonicprovider.addserver

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markadamson83.powerampsubsonicprovider.addserver.state.AddServerState
import com.markadamson83.powerampsubsonicprovider.app.CoroutineDispatchers
import com.markadamson83.powerampsubsonicprovider.domain.server.ServerRepository
import com.markadamson83.powerampsubsonicprovider.domain.validation.ServerValidationResult
import com.markadamson83.powerampsubsonicprovider.domain.validation.BasicServerValidator
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddServerViewModel(private val serverValidator: BasicServerValidator,
                         private val serverRepository: ServerRepository,
                         private val dispatchers: CoroutineDispatchers
) : ViewModel() {
    private val mutableAddServerState = MutableLiveData<AddServerState>()
    val addServerState: LiveData<AddServerState> = mutableAddServerState

    fun addServer(serverName: String, baseURL: String, username: String, password: String) {
        when (serverValidator.validate(serverName, baseURL, username, password)) {
            is ServerValidationResult.InvalidServerName ->
                mutableAddServerState.value = AddServerState.BadServerName
            is ServerValidationResult.InvalidURL ->
                mutableAddServerState.value = AddServerState.BadURL
            is ServerValidationResult.InvalidUsername ->
                mutableAddServerState.value = AddServerState.BadUsername
            is ServerValidationResult.InvalidPassword ->
                mutableAddServerState.value = AddServerState.BadPassword
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
        viewModelScope.launch {
            mutableAddServerState.value = AddServerState.Saving
            mutableAddServerState.value = withContext(dispatchers.background) {
                serverRepository.createAndAddServer(serverName, baseURL, username, password)
            }
        }
    }

    fun resetState() {
        mutableAddServerState.value = null
    }
}