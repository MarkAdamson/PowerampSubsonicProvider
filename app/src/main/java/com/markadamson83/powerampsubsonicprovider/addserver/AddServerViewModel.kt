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

    class UserRepository {
        val serverStore = InMemoryServerStore()

        fun createAndAddServer(
            serverName: String,
            baseURL: String,
            username: String,
            password: String
        ): AddServerState {
            return AddServerState.ServerExists(serverStore.createServer(serverName, baseURL, username, password))
        }
    }

    val serverStore = InMemoryServerStore()

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
                createAndAddServer(serverName, baseURL, username, password)
            }
        }
    }

    class InMemoryServerStore(private val servers: MutableList<Server> = mutableListOf()) {
        fun createServer(
            serverName: String,
            baseURL: String,
            username: String,
            password: String
        ): Server {
            val serverId = createServerIdFor(serverName)
            val server = Server(
                serverId,
                serverName,
                baseURL,
                username,
                password
            )
            saveServer(server)
            return server
        }

        private fun saveServer(server: Server) {
            servers.add(server)
        }

        private fun createServerIdFor(serverName: String): String {
            return serverName.filterNot { it.isWhitespace() } + "Id"
        }
    }

    private fun createAndAddServer(
        serverName: String,
        baseURL: String,
        username: String,
        password: String
    ): AddServerState {
        return AddServerState.ServerExists(serverStore.createServer(serverName, baseURL, username, password))
    }
}