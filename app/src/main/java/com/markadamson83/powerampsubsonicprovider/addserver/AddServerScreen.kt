package com.markadamson83.powerampsubsonicprovider.addserver

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
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
    var baseURL by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("")}
    var password by remember { mutableStateOf("") }
    val addServerState by addServerViewModel.addServerState.observeAsState()

    if(addServerState is AddServerState.ServerExists) {
        onServerAdded()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ScreenTitle(R.string.add_a_server)
        Spacer(modifier = Modifier.height(16.dp))
        ServerNameField(
            value = serverName,
            onValueChange = { serverName = it }
        )
        BaseURLField(
            value = baseURL,
            onValueChange = { baseURL = it }
        )
        UsernameField(
            value = username,
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
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        label = {
            Text(text = stringResource(R.string.server_name_hint))
        },
        onValueChange = onValueChange
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun BaseURLField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        label = {
            Text(text = stringResource(R.string.base_url_hint))
        },
        onValueChange = onValueChange
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun UsernameField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        label = {
            Text(text = stringResource(R.string.username_hint))
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