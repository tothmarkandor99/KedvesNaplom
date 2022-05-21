package com.webtic.kedvesnaplom.ui.main

import android.util.Log
import androidx.annotation.WorkerThread
import com.webtic.kedvesnaplom.model.Bejegyzes
import com.webtic.kedvesnaplom.network.BejegyzesService
import com.webtic.kedvesnaplom.persistence.BejegyzesDao
import java.lang.Exception
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val bejegyzesService: BejegyzesService,
    private val bejegyzesDao: BejegyzesDao
) {
    @WorkerThread
    suspend fun loadBejegyzesek(
        forceDownload: Boolean = false,
    ): List<Bejegyzes> {
        val bejegyzesek = bejegyzesDao.getBejegyzesList()
        if (forceDownload || bejegyzesek.isEmpty()) {
            try {
                val response = bejegyzesService.fetchBejegyzesList("hal")
                val frissBejegyzesek = response.map { b -> Bejegyzes(
                    b.azonosito, b.felhasznaloAzonosito, b.datum, b.tartalom
                ) }
                bejegyzesDao.clearBejegyzesList()
                bejegyzesDao.insertBejegyzesList(frissBejegyzesek)
                return frissBejegyzesek
            } catch (e: Exception) {
                Log.d("KN", e.message.toString())
            }
        }
        return bejegyzesek
    }

    @WorkerThread
    suspend fun deleteBejegyzes() {
        try {
            bejegyzesService.deleteBejegyzes("hal")
            val response = bejegyzesService.fetchBejegyzesList("hal")
            val frissBejegyzesek = response.map { b -> Bejegyzes(
                b.azonosito, b.felhasznaloAzonosito, b.datum, b.tartalom
            ) }
            bejegyzesDao.clearBejegyzesList()
            bejegyzesDao.insertBejegyzesList(frissBejegyzesek)
        } catch (e: Exception) {
            Log.d("KN", e.message.toString())
        }
    }
}