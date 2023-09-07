package com.markadamson83.powerampsubsonicprovider.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.markadamson83.powerampsubsonicprovider.domain.server.Server
import java.util.UUID

@Entity(tableName = "servers")
data class RoomServer (
    @PrimaryKey val serverId: UUID,
    @ColumnInfo(name = "server_name") val serverName: String,
    @ColumnInfo(name = "base_url") val baseURL: String,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "password") val password: String
) {
    constructor(server: Server) : this(
        UUID.fromString(server.serverId),
        server.serverName,
        server.baseURL,
        server.username,
        server.password
    )
    val server: Server
        get() = Server(serverId.toString(), serverName, baseURL, username, password)
}