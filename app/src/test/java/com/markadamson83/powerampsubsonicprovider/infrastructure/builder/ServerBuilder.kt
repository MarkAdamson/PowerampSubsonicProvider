package com.markadamson83.powerampsubsonicprovider.infrastructure.builder

import com.markadamson83.powerampsubsonicprovider.domain.server.Server
import java.util.UUID

class ServerBuilder {

    private var serverId = UUID.randomUUID().toString()
    private var serverName = "Demo Server"
    private var baseURL = "http://demo.subsonic.org"
    private var username = "guest1"
    private var password = "guest"

    companion object {
        fun aServer(): ServerBuilder {
            return ServerBuilder()
        }
    }

    fun withId(id: String): ServerBuilder {
        return this.apply {
            serverId = id
        }
    }

    fun withName(name: String): ServerBuilder {
        return this.apply {
            serverName = name
        }
    }

    fun withBaseURL(url: String): ServerBuilder {
        return this.apply {
            baseURL = url
        }
    }

    fun withUsername(username: String): ServerBuilder {
        return this.apply {
            this.username = username
        }
    }

    fun withPassword(password: String): ServerBuilder {
        return this.apply {
            this.password = password
        }
    }

    fun build(): Server {
        return Server(serverId, serverName, baseURL, username, password)
    }
}