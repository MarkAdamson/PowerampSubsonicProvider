package com.markadamson83.powerampsubsonicprovider.server

import retrofit2.http.GET

interface SubsonicAPI {
    @GET("/rest/ping.view?version=1.16.1&c=powerampsubsonicprovider&u=mark&p=UXCL75onal4MgVT3Kbum")
    suspend fun ping(): SubsonicResponse
}