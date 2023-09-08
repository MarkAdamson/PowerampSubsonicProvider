package com.markadamson83.powerampsubsonicprovider.domain.validation

interface ServerValidator {
    suspend fun validate(
        serverName: String,
        baseURL: String,
        username: String,
        password: String
    ): ServerValidationResult
}