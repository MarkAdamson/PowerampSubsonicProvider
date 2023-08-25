package com.markadamson83.powerampsubsonicprovider.server

import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class SubsonicServer constructor(baseURL: String, val username: String, val password: String) {
    val api: SubsonicAPI

    init {
        api = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
            .create(SubsonicAPI::class.java)
    }

    suspend fun ping(): SubsonicResponse {
        return api.ping()
    }
}