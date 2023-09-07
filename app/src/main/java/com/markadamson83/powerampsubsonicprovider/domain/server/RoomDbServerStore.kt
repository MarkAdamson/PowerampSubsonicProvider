package com.markadamson83.powerampsubsonicprovider.domain.server

import com.markadamson83.powerampsubsonicprovider.roomdb.PSPDatabase
import com.markadamson83.powerampsubsonicprovider.roomdb.RoomServer
import java.util.UUID

class RoomDbServerStore(
    private val db: PSPDatabase
) : ServerStore {
    override suspend fun servers(): List<Server> = db.serverDao().getAll().map { it.server }

    override suspend fun createServer(serverName: String, baseURL: String, username: String, password: String): Server =
        RoomServer(UUID.randomUUID(), serverName, baseURL, username, password).let {
            db.serverDao().insertAll(it)
            return it.server
        }

    override suspend fun deleteServer(serverId: String) { db.serverDao().delete(
        RoomServer(
            UUID.fromString(serverId), "", "", "", ""
        )
    ) }
}