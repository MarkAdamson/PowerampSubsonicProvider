package com.markadamson83.powerampsubsonicprovider.addserver.state

import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
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
    var currentInfoMessage by mutableStateOf(0)
    var isInfoMessageVisible by mutableStateOf(false)

    private var lastSubmittedServerName by mutableStateOf<String?>(null)
    private var lastSubmittedBaseURL by mutableStateOf<String?>(null)
    private var lastSubmittedUsername by mutableStateOf<String?>(null)
    private var lastSubmittedPassword by mutableStateOf<String?>(null)

    val showBadServerName : Boolean
        get() = isBadServerName && lastSubmittedServerName == serverName
    val showBadURL : Boolean
        get() = isBadURL && lastSubmittedBaseURL == baseURL
    val showBadUsername : Boolean
        get() = isBadUsername && lastSubmittedUsername == username
    val showBadPassword : Boolean
        get() = isBadPassword && lastSubmittedPassword == password

    fun toggleInfoMessage(@StringRes message: Int) = coroutineScope.launch {
        if(currentInfoMessage != message) {
            currentInfoMessage = message
            if (!isInfoMessageVisible) {
                isInfoMessageVisible = true
                delay(3000)
                isInfoMessageVisible = false
            }
        }
    }

    fun resetUIState() {
        currentInfoMessage = 0
        isInfoMessageVisible = false
        lastSubmittedServerName = serverName
        lastSubmittedBaseURL = baseURL
        lastSubmittedUsername = username
        lastSubmittedPassword = password
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