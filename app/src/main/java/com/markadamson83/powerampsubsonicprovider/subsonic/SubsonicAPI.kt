package com.markadamson83.powerampsubsonicprovider.subsonic

import retrofit2.http.GET
import retrofit2.http.Query

interface SubsonicAPI {
    @GET("/rest/ping.view?v=1.16.1&c=powerampsubsonicprovider")
    suspend fun ping(
        @Query("u") username: String,
        @Query("p") password: String
    ): SubsonicResponse
}