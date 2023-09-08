package com.markadamson83.powerampsubsonicprovider.subsonic

import com.markadamson83.powerampsubsonicprovider.domain.validation.BasicServerValidator
import com.markadamson83.powerampsubsonicprovider.domain.validation.ServerValidationResult

class SubsonicServerValidator : BasicServerValidator() {
    override suspend fun validate(
        serverName: String,
        baseURL: String,
        username: String,
        password: String
    ): ServerValidationResult {
        var result = super.validate(serverName, baseURL, username, password)

        if (result == ServerValidationResult.Valid) {
            val server = SubsonicServer(baseURL, username, password)
            val ping = server.ping()

            if (ping.status != "ok") {
                result = ServerValidationResult.InvalidCredentials
            }
        }

        return result
    }
}