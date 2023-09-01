package com.markadamson83.powerampsubsonicprovider.addserver

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.markadamson83.powerampsubsonicprovider.MainActivity
import com.markadamson83.powerampsubsonicprovider.domain.exceptions.BackendException
import com.markadamson83.powerampsubsonicprovider.domain.exceptions.ConnectionUnavailableException
import com.markadamson83.powerampsubsonicprovider.domain.exceptions.UnresponsiveServerException
import com.markadamson83.powerampsubsonicprovider.domain.server.InMemoryServerStore
import com.markadamson83.powerampsubsonicprovider.domain.server.Server
import com.markadamson83.powerampsubsonicprovider.domain.server.ServerStore
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class AddServerScreenTest {

    private val serverStore = InMemoryServerStore()
    private val addServerModule = module {
        factory<ServerStore> { serverStore }
    }

    private fun replaceServerStoreWith(serverStore: ServerStore) {
        val replaceModule = module {
            factory { serverStore }
        }
        loadKoinModules(replaceModule)
    }

    @get:Rule
    val addServerTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        loadKoinModules(addServerModule)
    }

    @Test
    fun testAddServer() {
        launchAddServerScreen(addServerTestRule) {
            typeServerName("Demo Server")
            typeBaseURL("http://demo.subsonic.org")
            typeUsername("guest1")
            typePassword("guest")
            submit()
        } verify {
            mainScreenIsPresent()
        }
    }

    @Test
    fun displayServerUnresponsiveError() {
        replaceServerStoreWith(UnresponsiveServerStore())

        launchAddServerScreen(addServerTestRule) {
            typeServerName("Bad Server")
            typeBaseURL("http://bad.demo.subsonic.org")
            typeUsername("guest1")
            typePassword("guest")
            submit()
        } verify {
            unresponsiveServerErrorIsDisplayed()
        }
    }

    @Test
    fun displayBackendError() {
        replaceServerStoreWith(IncorrectUserOrPasswordServerStore())

        launchAddServerScreen(addServerTestRule) {
            typeServerName("Demo Server")
            typeBaseURL("http://demo.subsonic.org")
            typeUsername("abc")
            typePassword("def")
            submit()
        } verify {
            backendErrorIsDisplayed()
        }
    }

    @Test
    fun displayOffLineError() {
        replaceServerStoreWith(OfflineServerStore())

        launchAddServerScreen(addServerTestRule) {
            typeServerName("Demo Server")
            typeBaseURL("http://demo.subsonic.org")
            typeUsername("guest1")
            typePassword("guest")
            submit()
        } verify {
            offlineErrorIsDisplayed()
        }
    }

    @After
    fun tearDown() {
        val resetModule = module {
            single { InMemoryServerStore() }
        }
        loadKoinModules(resetModule)
    }

    class UnresponsiveServerStore : ServerStore {
        override fun createServer(
            serverName: String,
            baseURL: String,
            username: String,
            password: String
        ): Server {
            throw UnresponsiveServerException()
        }
    }

    class IncorrectUserOrPasswordServerStore :
        ServerStore {
        override fun createServer(
            serverName: String,
            baseURL: String,
            username: String,
            password: String
        ): Server {
            throw BackendException()
        }
    }

    class OfflineServerStore : ServerStore {
        override fun createServer(
            serverName: String,
            baseURL: String,
            username: String,
            password: String
        ): Server {
            throw ConnectionUnavailableException()
        }
    }
}