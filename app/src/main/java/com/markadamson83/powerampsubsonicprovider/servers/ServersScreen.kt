package com.markadamson83.powerampsubsonicprovider.servers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.markadamson83.powerampsubsonicprovider.R
import com.markadamson83.powerampsubsonicprovider.domain.server.Server
import com.markadamson83.powerampsubsonicprovider.servers.state.ServersState

@Composable
fun ServersScreen(
    serversViewModel: ServersViewModel,
    onNewServer: () -> Unit
) {
    val addServerState by serversViewModel.serversState.observeAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = stringResource(id = R.string.servers))
        when (addServerState) {
            is ServersState.Servers -> {
                val servers = (addServerState as ServersState.Servers).servers
                if (servers.isEmpty()) {
                    Text(text = stringResource(id = R.string.no_servers))
                } else {
                    servers.forEach { server ->
                        Server(
                            server = server,
                            onDeleteServer = {
                                serversViewModel.deleteServer(server.serverId)
                            }
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

@Composable
private fun Server(
    server: Server,
    onDeleteServer: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .testTag(server.serverId),
        verticalAlignment = CenterVertically
    ) {
        Text(
            modifier = Modifier
                .testTag(stringResource(R.string.server))
                .weight(1f),
            text = server.serverName
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            modifier = Modifier.testTag(stringResource(R.string.delete_server) + "::" + server.serverId),
            onClick = onDeleteServer
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_delete),
                contentDescription = stringResource(R.string.delete_server)
            )
        }
    }
}