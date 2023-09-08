package com.markadamson83.powerampsubsonicprovider.domain.validation

sealed class ServerValidationResult {
    object InvalidServerName : ServerValidationResult()
    object InvalidURL : ServerValidationResult()
    object InvalidUsername : ServerValidationResult()
    object InvalidPassword : ServerValidationResult()
    object InvalidCredentials : ServerValidationResult()
    object BackendError : ServerValidationResult()
    object Valid : ServerValidationResult()
    object Timeout : ServerValidationResult()
}