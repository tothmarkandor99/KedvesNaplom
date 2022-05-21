package com.webtic.kedvesnaplom.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.webtic.kedvesnaplom.model.Bejegyzes

@Dao
interface BejegyzesDao {
    @Query("DELETE FROM Bejegyzes")
    suspend fun clearBejegyzesList()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBejegyzesList(bejegyzesek: List<Bejegyzes>)

    @Query("SELECT * FROM Bejegyzes WHERE azonosito = :azonosito_")
    suspend fun getBejegyzes(azonosito_: Int): Bejegyzes?

    @Query("SELECT * FROM Bejegyzes ORDER BY datum DESC")
    suspend fun getBejegyzesList(): List<Bejegyzes>
}