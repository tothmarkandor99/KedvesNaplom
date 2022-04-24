package com.webtic.kedvesnaplom.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.webtic.kedvesnaplom.model.Bejegyzes

//@Database(entities = [Bejegyzes::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bejegyzesDao(): BejegyzesDao
}