package com.markadamson83.powerampsubsonicprovider.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ServerDao {
    @Query("SELECT * FROM servers")
    fun getAll(): List<RoomServer>

    @Insert
    fun insertAll(vararg servers: RoomServer)

    @Delete
    fun delete(server: RoomServer)
}