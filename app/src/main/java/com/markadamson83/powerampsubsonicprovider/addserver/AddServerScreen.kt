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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.markadamson83.powerampsubsonicprovider.R
import com.markadamson83.powerampsubsonicprovider.addserver.state.AddServerState

@Composable
@Preview(device = Devices.PIXEL_4_XL)
fun AddServerScreen(
    addServerViewModel: AddServerViewModel,
    onServerAdded: () -> Unit
) {
    var serverName by remember { mutableStateOf("") }
    var isBadServerName by remember { mutableStateOf(false) }
    var baseURL by remember { mutableStateOf("") }
    var isBadURL by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("")}
    var isBadUsername by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    val addServerState by addServerViewModel.addServerState.observeAsState()

    when (addServerState) {
        is AddServerState.ServerExists ->
            onServerAdded()
        is AddServerState.BadServerName ->
            isBadServerName = true
        is AddServerState.BadURL ->
            isBadURL = true
        is AddServerState.BadUsername ->
            isBadUsername = true
        is AddServerState.BackendError ->
            InfoMessage(R.string.backend_error)
        is AddServerState.UnresponsiveServer ->
            InfoMessage(R.string.unresponsive_server_error)
        is AddServerState.Offline ->
            InfoMessage(R.string.offline_error)
        else -> {}
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
                value = serverName,
                isError = isBadServerName,
                onValueChange = { serverName = it }
            )
            BaseURLField(
                value = baseURL,
                isError = isBadURL,
                onValueChange = { baseURL = it }
            )
            UsernameField(
                value = username,
                isError = isBadUsername,
                onValueChange = { username = it }
            )
            PasswordField(
                value = password,
                onValueChange = { password = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    addServerViewModel.addServer(serverName, baseURL, username, password)
                }
            ) {
                Text(text = stringResource(R.string.add_server))
            }
        }
    }
}

@Composable
fun InfoMessage(@StringRes resourceId: Int) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.error,
    ) {
        Text(
            text = stringResource(resourceId)
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
        modifier = Modifier.fillMaxWidth(),
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
        modifier = Modifier.fillMaxWidth(),
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
        modifier = Modifier.fillMaxWidth(),
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
    onValueChange: (String) -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        trailingIcon = {
            VisibilityToggle(isVisible) {
                isVisible = !isVisible
            }
        },
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        label = {
            Text(text = stringResource(R.string.password_hint))
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