package com.markadamson83.powerampsubsonicprovider.servers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.markadamson83.powerampsubsonicprovider.R

@Composable
fun ServersScreen(
    onNewServer: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = stringResource(id = R.string.servers))
        Text(text = stringResource(id = R.string.no_servers))
        Button(onClick = onNewServer) {
            Text(text = stringResource(id = R.string.new_server))
        }
    }
}