package com.markadamson83.powerampsubsonicprovider.domain.validation

import androidx.core.util.PatternsCompat

class ServerValidator {
    fun validate(
        baseURL: String,
        username: String,
        password: String
    ): ServerValidationResult {
        val result = if (!PatternsCompat.WEB_URL.matcher(baseURL).matches()) {
            ServerValidationResult.InvalidURL
        } else if (username.isEmpty()) {
            ServerValidationResult.InvalidUsername
        } else if (password.isEmpty()) {
            ServerValidationResult.InvalidPassword
        } else TODO()
        return result
    }
}