package com.markadamson83.powerampsubsonicprovider.addserver

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
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.markadamson83.powerampsubsonicprovider.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(device = Devices.PIXEL_4_XL)
fun AddServer() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.add_a_server),
                style = typography.titleLarge
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            label = {
                Text(text = stringResource(R.string.base_url_hint))
            },
            onValueChange = { }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            label = {
                Text(text = stringResource(R.string.username_hint))
            },
            onValueChange = { }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            label = {
                Text(text = stringResource(R.string.password_hint))
            },
            onValueChange = { }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { }
        ) {
            Text(text = stringResource(R.string.add_server))
        }
    }
}