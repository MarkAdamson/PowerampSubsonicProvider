package com.markadamson83.powerampsubsonicprovider.addserver

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.markadamson83.powerampsubsonicprovider.MainActivity
import com.markadamson83.powerampsubsonicprovider.domain.exceptions.BackendException
import com.markadamson83.powerampsubsonicprovider.domain.exceptions.ConnectionUnavailableException
import com.markadamson83.powerampsubsonicprovider.domain.exceptions.UnresponsiveServerException
import com.markadamson83.powerampsubsonicprovider.domain.server.RoomDbServerStore
import com.markadamson83.powerampsubsonicprovider.domain.server.Server
import com.markadamson83.powerampsubsonicprovider.domain.server.ServerStore
import com.markadamson83.powerampsubsonicprovider.domain.validation.BasicServerValidator
import com.markadamson83.powerampsubsonicprovider.domain.validation.ServerValidationResult
import com.markadamson83.powerampsubsonicprovider.domain.validation.ServerValidator
import com.markadamson83.powerampsubsonicprovider.roomdb.PSPDatabase
import kotlinx.coroutines.delay
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class AddServerScreenTest {
    private val db = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        PSPDatabase::class.java
    ).build()


    private val addServerModule = module {
        factory<ServerStore> { RoomDbServerStore(db) }
        factory<ServerValidator> { BasicServerValidator() }
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
    fun displayBadServerNameError() {
        launchAddServerScreen(addServerTestRule) {
            typeServerName("")
            typeBaseURL("http://demo.subsonic.org")
            typeUsername("guest1")
            typePassword("guest")
            submit()
        } verify {
            badServerNameErrorIsDisplayed()
        }
    }

    @Test
    fun resetBadServerNameError() {
        launchAddServerScreen(addServerTestRule) {
            typeServerName("")
            typeBaseURL("http://demo.subsonic.org")
            typeUsername("guest1")
            typePassword("guest")
            submit()
            typeServerName("Demo Server")
        } verify {
            badServerNameErrorIsNotDisplayed()
        }
    }

    @Test
    fun reenterBadServerName() {
        launchAddServerScreen(addServerTestRule) {
            typeServerName("")
            typeBaseURL("http://demo.subsonic.org")
            typeUsername("guest1")
            typePassword("guest")
            submit()
            typeServerName("Demo Server")
            clearServerName()
        } verify {
            badServerNameErrorIsNotDisplayed()
        }
    }

    @Test
    fun persistServerNameOnOrientationChange() {
        launchAddServerScreen(addServerTestRule) {
            setPortraitOrientation()
            typeServerName("Demo Server")
            setLandscapeOrientation()
        } verify {
            serverNameIs("Demo Server")
        }
    }

    @Test
    fun displayBadURLError() {
        launchAddServerScreen(addServerTestRule) {
            typeServerName("Bad Server")
            typeBaseURL("!badURL!")
            typeUsername("guest1")
            typePassword("guest")
            submit()
        } verify {
            badURLErrorIsDisplayed()
        }
    }

    @Test
    fun resetBadURLError() {
        launchAddServerScreen(addServerTestRule) {
            typeServerName("Bad Server")
            typeBaseURL("!badURL!")
            typeUsername("guest1")
            typePassword("guest")
            submit()
            clearBaseURL()
        } verify {
            badURLErrorIsNotDisplayed()
        }
    }

    @Test
    fun reenterBadURL() {
        launchAddServerScreen(addServerTestRule) {
            typeServerName("Bad Server")
            typeBaseURL("!badURL!")
            typeUsername("guest1")
            typePassword("guest")
            submit()
            clearBaseURL()
            typeBaseURL("!badURL!")
        } verify {
            badURLErrorIsNotDisplayed()
        }
    }

    @Test
    fun persistURLOnOrientationChange() {
        launchAddServerScreen(addServerTestRule) {
            setPortraitOrientation()
            typeBaseURL("http://demo.subsonic.org")
            setLandscapeOrientation()
        } verify {
            urlIs("http://demo.subsonic.org")
        }
    }

    @Test
    fun displayBadUsernameError() {
        launchAddServerScreen(addServerTestRule) {
            typeServerName("Demo Server")
            typeBaseURL("http://demo.subsonic.org")
            typeUsername("")
            typePassword("guest")
            submit()
        } verify {
            badUsernameErrorIsDisplayed()
        }
    }

    @Test
    fun resetBadUsernameError() {
        launchAddServerScreen(addServerTestRule) {
            typeServerName("Demo Server")
            typeBaseURL("http://demo.subsonic.org")
            typeUsername("")
            typePassword("guest")
            submit()
            typeUsername("guest1")
        } verify {
            badUsernameErrorIsNotDisplayed()
        }
    }

    @Test
    fun reenterBadUsername() {
        launchAddServerScreen(addServerTestRule) {
            typeServerName("Demo Server")
            typeBaseURL("http://demo.subsonic.org")
            typeUsername("")
            typePassword("guest")
            submit()
            typeUsername("guest1")
            clearUsername()
        } verify {
            badUsernameErrorIsNotDisplayed()
        }
    }

    @Test
    fun persistUsernameOnOrientationChange() {
        launchAddServerScreen(addServerTestRule) {
            setPortraitOrientation()
            typeUsername("guest1")
            setLandscapeOrientation()
        } verify {
            usernameIs("guest1")
        }
    }

    @Test
    fun displayBadPasswordError() {
        launchAddServerScreen(addServerTestRule) {
            typeServerName("Demo Server")
            typeBaseURL("http://demo.subsonic.org")
            typeUsername("guest1")
            typePassword("")
            submit()
        } verify {
            badPasswordErrorIsDisplayed()
        }
    }

    @Test
    fun resetBadPasswordError() {
        launchAddServerScreen(addServerTestRule) {
            typeServerName("Demo Server")
            typeBaseURL("http://demo.subsonic.org")
            typeUsername("guest1")
            typePassword("")
            submit()
            typePassword("guest")
        } verify {
            badPasswordErrorIsNotDisplayed()
        }
    }

    @Test
    fun reenterBadPassword() {
        launchAddServerScreen(addServerTestRule) {
            typeServerName("Demo Server")
            typeBaseURL("http://demo.subsonic.org")
            typeUsername("guest1")
            typePassword("")
            submit()
            typePassword("guest")
            clearPassword()
        } verify {
            badPasswordErrorIsNotDisplayed()
        }
    }

    @Test
    fun persistPasswordOnOrientationChange() {
        launchAddServerScreen(addServerTestRule) {
            setPortraitOrientation()
            typePassword("guest")
            setLandscapeOrientation()
            togglePasswordVisibility()
        } verify {
            passwordIs("guest")
        }
    }

    @Test
    fun persistPasswordVisibilityOnOrientationChange() {
        launchAddServerScreen(addServerTestRule) {
            setPortraitOrientation()
            togglePasswordVisibility()
            setLandscapeOrientation()
        } verify {
            passwordIsVisible()
        }
    }

    @Test
    fun displayBadCredentialsError() {
        replaceValidatorWith(BadCredentialsServerValidator())
        launchAddServerScreen(addServerTestRule) {
            typeServerName("Demo Server")
            typeBaseURL("http://demo.subsonic.org")
            typeUsername("abc")
            typePassword("def")
            submit()
        } verify {
            badCredentialsErrorIsDisplayed()
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

    @Test
    fun submitWhileInfoMessageDisplayed() {
        replaceServerStoreWith(OfflineServerStore())

        launchAddServerScreen(addServerTestRule) {
            typeServerName("Demo Server")
            typeBaseURL("http://demo.subsonic.org")
            typeUsername("guest1")
            typePassword("guest")
            submit()
            submit()
        } verify {
            offlineErrorIsDisplayed()
        }
    }

    @Test
    fun displayBlockingSaving() {
        replaceServerStoreWith(DelayingServerStore())

        launchAddServerScreen(addServerTestRule) {
            typeServerName("Demo Server")
            typeBaseURL("http://demo.subsonic.org")
            typeUsername("guest1")
            typePassword("guest")
            submit()
        } verify {
            blockingSavingIsDisplayed()
        }
    }

    private fun replaceServerStoreWith(serverStore: ServerStore) {
        val replaceModule = module {
            factory { serverStore }
        }
        loadKoinModules(replaceModule)
    }

    private fun replaceValidatorWith(validator: ServerValidator) {
        val replaceModule = module {
            factory { validator }
        }
        loadKoinModules(replaceModule)
    }

    class UnresponsiveServerStore : ServerStore {
        override suspend fun servers(): List<Server> = emptyList()

        override suspend fun createServer(
            serverName: String,
            baseURL: String,
            username: String,
            password: String
        ): Server {
            throw UnresponsiveServerException()
        }

        override suspend fun deleteServer(serverId: String) {
            TODO("Not yet implemented")
        }
    }

    class IncorrectUserOrPasswordServerStore :
        ServerStore {
        override suspend fun servers(): List<Server> = emptyList()

        override suspend fun createServer(
            serverName: String,
            baseURL: String,
            username: String,
            password: String
        ): Server {
            throw BackendException()
        }

        override suspend fun deleteServer(serverId: String) {
            TODO("Not yet implemented")
        }
    }

    class OfflineServerStore : ServerStore {
        override suspend fun servers(): List<Server> = emptyList()

        override suspend fun createServer(
            serverName: String,
            baseURL: String,
            username: String,
            password: String
        ): Server {
            throw ConnectionUnavailableException()
        }

        override suspend fun deleteServer(serverId: String) {
            TODO("Not yet implemented")
        }
    }

    class DelayingServerStore : ServerStore {
        override suspend fun servers(): List<Server> = emptyList()

        override suspend fun createServer(
            serverName: String,
            baseURL: String,
            username: String,
            password: String
        ): Server {
            delay(1000)
            return Server("someId", serverName, baseURL, username, password)
        }

        override suspend fun deleteServer(serverId: String) {
            TODO("Not yet implemented")
        }
    }

    class BadCredentialsServerValidator : ServerValidator {

        override suspend fun validate(
            serverName: String,
            baseURL: String,
            username: String,
            password: String
        ): ServerValidationResult {
            return ServerValidationResult.InvalidCredentials
        }
    }
}