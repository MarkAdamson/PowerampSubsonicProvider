package com.markadamson83.powerampsubsonicprovider.subsonic

import retrofit2.http.GET

interface SubsonicAPI {
    @GET("/rest/ping.view?version=1.16.1&c=powerampsubsonicprovider&u=user&p=password")
    suspend fun ping(): SubsonicResponse
}