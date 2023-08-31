package com.markadamson83.powerampsubsonicprovider

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.markadamson83.powerampsubsonicprovider.addserver.AddServerScreen
import com.markadamson83.powerampsubsonicprovider.addserver.AddServerViewModel
import com.markadamson83.powerampsubsonicprovider.server.SubsonicResponse
import com.markadamson83.powerampsubsonicprovider.server.SubsonicServer
import com.markadamson83.powerampsubsonicprovider.servers.Servers
import com.markadamson83.powerampsubsonicprovider.ui.theme.PowerampSubsonicProviderTheme
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val addServerViewModel by viewModel<AddServerViewModel>()

    companion object {
        private const val ADD_SERVER = "addServer"
        private const val SERVERS = "servers"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            PowerampSubsonicProviderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = ADD_SERVER) {
                        composable(ADD_SERVER) {
                            AddServerScreen(addServerViewModel) {
                                navController.navigate(SERVERS)
                            }
                        }
                        composable(SERVERS) {
                            Servers()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PingServer() {
    // Returns a scope that's cancelled when F is removed from composition
    val coroutineScope = rememberCoroutineScope()

    val server = SubsonicServer("https://baseURL", "user", "password")
    var (response, setResponse) = remember { mutableStateOf<SubsonicResponse?>(null) }

    val getLocationOnClick: () -> Unit = {
        coroutineScope.launch {
            response = server.ping()
            response!!.version?.let { Log.d(response!!.status, it) }
        }
    }

    Button(onClick = getLocationOnClick) {
        Text("detectLocation")
    }

    Text(
        text = "Server response: ${response?.status} ${response?.version}"
    )
}