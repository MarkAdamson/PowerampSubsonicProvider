package com.markadamson83.powerampsubsonicprovider.addserver

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.markadamson83.powerampsubsonicprovider.R
import com.markadamson83.powerampsubsonicprovider.addserver.state.AddServerScreenState
import com.markadamson83.powerampsubsonicprovider.addserver.state.AddServerState

@Composable
@Preview(device = Devices.PIXEL_4_XL)
fun AddServerScreen(
    addServerViewModel: AddServerViewModel,
    onServerAdded: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val screenState by remember { mutableStateOf(AddServerScreenState(coroutineScope)) }
    val addServerState by addServerViewModel.addServerState.observeAsState()

    screenState.isBadServerName = addServerState is AddServerState.BadServerName
    screenState.isBadURL = addServerState is AddServerState.BadURL
    screenState.isBadUsername = addServerState is AddServerState.BadUsername
    screenState.isBadPassword = addServerState is AddServerState.BadPassword

    when (addServerState) {
        is AddServerState.ServerExists ->
            onServerAdded()
        is AddServerState.BackendError ->
            screenState.toggleInfoMessage(stringResource(R.string.backend_error))
        is AddServerState.UnresponsiveServer ->
            screenState.toggleInfoMessage(stringResource(R.string.unresponsive_server_error))
        is AddServerState.Offline ->
            screenState.toggleInfoMessage(stringResource(R.string.offline_error))
        else -> { }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ScreenTitle(R.string.add_a_server)
            Spacer(modifier = Modifier.height(16.dp))
            ServerNameField(
                value = screenState.serverName,
                isError = screenState.showBadServerName,
                onValueChange = {
                    screenState.serverName = it
                    screenState.resetLastSubmittedServerName()
                }
            )
            BaseURLField(
                value = screenState.baseURL,
                isError = screenState.showBadURL,
                onValueChange = {
                    screenState.baseURL = it
                    screenState.resetLastSubmittedBaseURL()
                }
            )
            UsernameField(
                value = screenState.username,
                isError = screenState.showBadUsername,
                onValueChange = {
                    screenState.username = it
                    screenState.resetLastSubmittedUsername()
                }
            )
            PasswordField(
                value = screenState.password,
                isError = screenState.showBadPassword,
                onValueChange = {
                    screenState.password = it
                    screenState.resetLastSubmittedPassword()
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    screenState.resetUIState()
                    with(screenState) {
                        addServerViewModel.addServer(serverName, baseURL, username, password)
                    }
                }
            ) {
                Text(text = stringResource(R.string.add_server))
            }
        }

        SnackbarHost(
            hostState = screenState.snackbarHostState,
            snackbar = {
                InfoMessage(message = it.visuals.message)
            }
        )
    }
}

@Composable
fun InfoMessage(
    message: String
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.error),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = message,
            color = MaterialTheme.colorScheme.onError,
        )
    }
}

@Composable
private fun ScreenTitle(@StringRes resource: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(resource),
            style = typography.titleLarge
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ServerNameField(
    value: String,
    isError: Boolean,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(stringResource(id = R.string.server_name_hint)),
        value = value,
        isError = isError,
        label = {
            val resource = if(isError) R.string.bad_server_name_error else R.string.server_name_hint
            Text(text = stringResource(resource))
        },
        onValueChange = onValueChange
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun BaseURLField(
    value: String,
    isError: Boolean,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier =
            Modifier
                .fillMaxWidth()
                .testTag(stringResource(id = R.string.base_url_hint)),
        value = value,
        isError = isError,
        label = {
            val resource = if(isError) R.string.bad_url_error else R.string.base_url_hint
            Text(text = stringResource(id = resource))
        },
        onValueChange = onValueChange
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun UsernameField(
    value: String,
    isError: Boolean,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier =
            Modifier
                .fillMaxWidth()
                .testTag(stringResource(id = R.string.username_hint)),
        value = value,
        isError = isError,
        label = {
            val resource = if(isError) R.string.bad_username_error else R.string.username_hint
            Text(text = stringResource(resource))
        },
        onValueChange = onValueChange
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun PasswordField(
    value: String,
    isError: Boolean,
    onValueChange: (String) -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier =
            Modifier
                .fillMaxWidth()
                .testTag(stringResource(id = R.string.password_hint)),
        value = value,
        isError = isError,
        trailingIcon = {
            VisibilityToggle(isVisible) {
                isVisible = !isVisible
            }
        },
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        label = {
            val resource = if(isError) R.string.bad_password_error else R.string.password_hint
            Text(text = stringResource(resource))
        },
        onValueChange = onValueChange
    )
}

@Composable
private fun VisibilityToggle(
    isVisible: Boolean,
    onToggle: () -> Unit = {}
) {
    var isVisible1 = isVisible
    IconButton(onClick = onToggle) {
        Icon(
            painter = painterResource(id = if (isVisible1) R.drawable.ic_visibility_off_24 else R.drawable.ic_visibility),
            contentDescription = stringResource(R.string.toggle_password_visibility)
        )
    }
}