package com.markadamson83.powerampsubsonicprovider.domain.validation

sealed class ServerValidationResult {
    object InvalidURL : ServerValidationResult()
    object InvalidUsername : ServerValidationResult()
    object InvalidPassword : ServerValidationResult()
}