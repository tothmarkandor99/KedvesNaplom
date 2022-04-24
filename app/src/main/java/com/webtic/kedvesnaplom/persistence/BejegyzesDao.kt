package com.webtic.kedvesnaplom.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.webtic.kedvesnaplom.model.Bejegyzes

//@Dao
interface BejegyzesDao {
    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBejegyzesList(bejegyzesek: List<Bejegyzes>)

    //@Query("SELECT * FROM Bejegyzes WHERE id = :id_")
    suspend fun getBejegyzes(id_: Int): Bejegyzes?

    //@Query("SELECT * FROM Bejegyzes")
    suspend fun getBejegyzesList(): List<Bejegyzes>
}