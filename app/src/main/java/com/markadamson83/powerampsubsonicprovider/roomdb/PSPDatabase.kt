package com.markadamson83.powerampsubsonicprovider.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RoomServer::class], version = 1)
abstract class PSPDatabase : RoomDatabase() {
    abstract fun serverDao(): ServerDao
}