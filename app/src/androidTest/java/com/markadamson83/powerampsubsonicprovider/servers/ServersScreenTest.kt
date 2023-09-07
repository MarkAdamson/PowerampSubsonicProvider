package com.markadamson83.powerampsubsonicprovider.servers

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.markadamson83.powerampsubsonicprovider.MainActivity
import com.markadamson83.powerampsubsonicprovider.domain.server.InMemoryServerStore
import com.markadamson83.powerampsubsonicprovider.domain.server.RoomDbServerStore
import com.markadamson83.powerampsubsonicprovider.domain.server.Server
import com.markadamson83.powerampsubsonicprovider.domain.server.ServerStore
import com.markadamson83.powerampsubsonicprovider.roomdb.PSPDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

typealias MainActivityTestRule = AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>

class ServersScreenTest {
    private val db = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        PSPDatabase::class.java
    ).build()

    private val addServerModule = module {
        factory<ServerStore> { RoomDbServerStore(db) }
    }

    @get:Rule
    val serversTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        loadKoinModules(addServerModule)
    }

    @Test
    fun newServer() {
        launchServersScreen(serversTestRule) {
            clickNewServer()
        } verify {
            addServerScreenIsDisplayed()
        }
    }

    @Test
    fun displayServers() {
        val server = Server("serverId", "serverName", "1.2.3.4", "username", "password")
        replaceServerStoreWith(InMemoryServerStore(mutableListOf(server)))

        launchServersScreen(serversTestRule) {
            // no operation
        } verify {
            serverIsDisplayed(server.serverName)
        }
    }

    @Test
    fun displayNoServersMessage() {
        launchServersScreen(serversTestRule) {
            // no operation
        } verify {
            noServersMessageIsDisplayed()
        }
    }

    @Test
    fun dontDisplayNoServersMessage() {
        val server = Server("serverId", "serverName", "1.2.3.4", "username", "password")
        replaceServerStoreWith(InMemoryServerStore(mutableListOf(server)))

        launchServersScreen(serversTestRule) {
            // no operation
        } verify {
            noServersMessageIsNotDisplayed()
        }
    }

    @Test
    fun deleteServer() {
        val server = Server("serverId", "serverName", "1.2.3.4", "username", "password")
        replaceServerStoreWith(InMemoryServerStore(mutableListOf(server)))

        launchServersScreen(serversTestRule) {
            deleteServer(server.serverId)
        } verify {
            serverIsNotDisplayed(server.serverName)
        }
    }

    @After
    fun tearDown() {
        replaceServerStoreWith(RoomDbServerStore(db))
    }

    private fun replaceServerStoreWith(serverStore: ServerStore) {
        val replaceModule = module {
            factory { serverStore }
        }
        loadKoinModules(replaceModule)
    }
}