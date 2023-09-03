package com.markadamson83.powerampsubsonicprovider.addserver.state

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AddServerScreenState(
    private val coroutineScope: CoroutineScope
) {
    var serverName by mutableStateOf("")
    var isBadServerName by mutableStateOf(false)
    var baseURL by mutableStateOf("")
    var isBadURL by mutableStateOf(false)
    var username by mutableStateOf("")
    var isBadUsername by mutableStateOf(false)
    var password by mutableStateOf("")
    var isBadPassword by mutableStateOf(false)

    val snackbarHostState = SnackbarHostState()

    private var lastSubmittedServerName by mutableStateOf<String?>(null)
    private var lastSubmittedBaseURL by mutableStateOf<String?>(null)
    private var lastSubmittedUsername by mutableStateOf<String?>(null)
    private var lastSubmittedPassword by mutableStateOf<String?>(null)
    private var lastInfoMessage by mutableStateOf("")

    val showBadServerName : Boolean
        get() = isBadServerName && lastSubmittedServerName == serverName
    val showBadURL : Boolean
        get() = isBadURL && lastSubmittedBaseURL == baseURL
    val showBadUsername : Boolean
        get() = isBadUsername && lastSubmittedUsername == username
    val showBadPassword : Boolean
        get() = isBadPassword && lastSubmittedPassword == password

    fun toggleInfoMessage(message: String) {
        if (message != lastInfoMessage) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
            }
            lastInfoMessage = message
        }
    }

    fun resetUIState() {
        lastSubmittedServerName = serverName
        lastSubmittedBaseURL = baseURL
        lastSubmittedUsername = username
        lastSubmittedPassword = password
        lastInfoMessage = ""
    }

    fun resetLastSubmittedServerName() {
        lastSubmittedServerName = null
    }

    fun resetLastSubmittedBaseURL() {
        lastSubmittedBaseURL = null
    }

    fun resetLastSubmittedUsername() {
        lastSubmittedUsername = null
    }

    fun resetLastSubmittedPassword() {
        lastSubmittedPassword = null
    }
}