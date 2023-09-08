package com.markadamson83.powerampsubsonicprovider.addserver

import com.markadamson83.powerampsubsonicprovider.InstantTaskExecutorExtension
import com.markadamson83.powerampsubsonicprovider.addserver.state.AddServerState
import com.markadamson83.powerampsubsonicprovider.app.TestDispatchers
import com.markadamson83.powerampsubsonicprovider.domain.server.InMemoryServerStore
import com.markadamson83.powerampsubsonicprovider.domain.server.ServerRepository
import com.markadamson83.powerampsubsonicprovider.domain.validation.BasicServerValidator
import com.markadamson83.powerampsubsonicprovider.domain.validation.ServerValidationResult
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@ExtendWith(InstantTaskExecutorExtension::class)
class ServerValidationTest {

    private val viewModel = AddServerViewModel(BasicServerValidator(), ServerRepository(InMemoryServerStore()), TestDispatchers())

    @Test
    fun invalidServerName() {
        viewModel.addServer("", "http://demo.subsonic.org", ":username:", ":password:")

        assertEquals(AddServerState.BadServerName, viewModel.addServerState.value)
    }

    @ParameterizedTest
    @CsvSource(
        "''",
        "'url'",
        "'http://'",
        "'ftp://demo.subsonic.org'",
    )
    fun invalidURL(url: String) {
        viewModel.addServer(":serverName:", url, ":username:", ":password:")

        assertEquals(AddServerState.BadURL, viewModel.addServerState.value)
    }

    @Test
    fun invalidUsername() {
        viewModel.addServer(":serverName:", "http://demo.subsonic.org", "", ":password:")

        assertEquals(AddServerState.BadUsername, viewModel.addServerState.value)
    }

    @Test
    fun invalidPassword() {
        viewModel.addServer(":serverName:", "http://demo.subsonic.org", ":username:", "")

        assertEquals(AddServerState.BadPassword, viewModel.addServerState.value)
    }

    @Test
    fun validServer() = runBlocking {
        val result = BasicServerValidator().validate("Demo Server", "http://demo.subsonic.org", "guest1", "guest")
        assertEquals(ServerValidationResult.Valid, result)
    }
}