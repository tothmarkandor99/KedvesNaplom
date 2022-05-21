package com.webtic.kedvesnaplom.ui.details

import android.util.Log
import androidx.annotation.WorkerThread
import com.webtic.kedvesnaplom.model.Bejegyzes
import com.webtic.kedvesnaplom.network.BejegyzesService
import com.webtic.kedvesnaplom.network.dto.PutBejegyzesDto
import com.webtic.kedvesnaplom.persistence.BejegyzesDao
import com.webtic.kedvesnaplom.ui.main.MainRepository
import java.lang.Exception
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val bejegyzesService: BejegyzesService,
    private val bejegyzesDao: BejegyzesDao,
    private val mainRepository: MainRepository,
) {

    @WorkerThread
    suspend fun getBejegyzes(azonosito: Int): Bejegyzes? {
        return bejegyzesDao.getBejegyzes(azonosito)
    }

    @WorkerThread
    suspend fun putBejegyzes(bejegyzes: PutBejegyzesDto) {
        try {
            bejegyzesService.putBejegyzes(bejegyzes)
            mainRepository.loadBejegyzesek(true)
        } catch (e: Exception) {
            Log.d("KN", e.message.toString())
        }
    }
}