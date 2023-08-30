package com.markadamson83.powerampsubsonicprovider.addserver

import com.markadamson83.powerampsubsonicprovider.InstantTaskExecutorExtension
import com.markadamson83.powerampsubsonicprovider.addserver.state.AddServerState
import com.markadamson83.powerampsubsonicprovider.domain.validation.ServerValidator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@ExtendWith(InstantTaskExecutorExtension::class)
class ServerValidationTest {

    @ParameterizedTest
    @CsvSource(
        "''",
        "'url'",
        "'http://'"
    )
    fun invalidURL(url: String) {
        val viewModel = AddServerViewModel(ServerValidator())

        viewModel.addServer(url, ":username:", ":password:")

        assertEquals(AddServerState.BadURL, viewModel.addServerState.value)
    }

    @Test
    fun invalidUsername() {
        val viewModel = AddServerViewModel(ServerValidator())

        viewModel.addServer("http://demo.subsonic.org", "", ":password:")

        assertEquals(AddServerState.BadUsername, viewModel.addServerState.value)
    }

    @Test
    fun invalidPassword() {
        val viewModel = AddServerViewModel(ServerValidator())

        viewModel.addServer("http://demo.subsonic.org", ":username:", "")

        assertEquals(AddServerState.BadPassword, viewModel.addServerState.value)
    }
}