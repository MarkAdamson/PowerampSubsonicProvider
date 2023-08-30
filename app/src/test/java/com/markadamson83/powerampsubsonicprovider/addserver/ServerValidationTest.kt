package com.markadamson83.powerampsubsonicprovider.addserver

import com.markadamson83.powerampsubsonicprovider.InstantTaskExecutorExtension
import com.markadamson83.powerampsubsonicprovider.addserver.state.AddServerState
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class ServerValidationTest {

    @Test
    fun invalidURL() {
        val viewModel = AddServerViewModel()

        viewModel.addServer("invalidURL", ":user:", ":password:")

        assertEquals(AddServerState.InvalidURL, viewModel.addServerState.value)
    }
}