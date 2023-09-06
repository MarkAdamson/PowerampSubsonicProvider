package com.markadamson83.powerampsubsonicprovider.servers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.markadamson83.powerampsubsonicprovider.R
import com.markadamson83.powerampsubsonicprovider.servers.state.ServersState

@Composable
fun ServersScreen(
    serversViewModel: ServersViewModel,
    onNewServer: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = stringResource(id = R.string.servers))
        when (serversViewModel.serversState.value) {
            is ServersState.Servers ->
                (serversViewModel.serversState.value as ServersState.Servers).servers.also { servers ->
                    if (servers.isEmpty()) {
                        Text(text = stringResource(id = R.string.no_servers))
                    } else {
                        servers.forEach { server ->
                            Text(
                                modifier = Modifier.testTag(stringResource(R.string.server)),
                                text = server.serverName
                            )
                        }
                    }
                }
            else -> {
                serversViewModel.getServers()
            }
        }
        Button(onClick = onNewServer) {
            Text(text = stringResource(id = R.string.new_server))
        }
    }
}