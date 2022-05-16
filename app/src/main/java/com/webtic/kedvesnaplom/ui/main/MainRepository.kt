package com.webtic.kedvesnaplom.ui.main

import android.util.Log
import androidx.annotation.WorkerThread
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.suspendOnSuccess
import com.webtic.kedvesnaplom.model.Bejegyzes
import com.webtic.kedvesnaplom.network.BejegyzesService
import com.webtic.kedvesnaplom.network.dto.DeleteBejegyzesDto
import com.webtic.kedvesnaplom.persistence.BejegyzesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import retrofit2.await
import retrofit2.awaitResponse
import java.lang.Exception
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val bejegyzesService: BejegyzesService,
    private val bejegyzesDao: BejegyzesDao
) {

    @WorkerThread
    fun loadBejegyzesek(
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: (String) -> Unit
    ) = flow {
        val bejegyzesek: List<Bejegyzes> = bejegyzesDao.getBejegyzesList()
        if (bejegyzesek.isEmpty()) {
            try {
                val response = bejegyzesService.fetchBejegyzesList("hal")
                val frissBejegyzesek = response.map { b -> Bejegyzes(
                    b.azonosito, b.felhasznaloAzonosito, b.datum, b.tartalom
                ) }
                bejegyzesDao.insertBejegyzesList(frissBejegyzesek)
                emit(frissBejegyzesek)
            } catch (e: Exception) {
                onError("Hálózati hiba történt")
            }
        } else {
            emit(bejegyzesek)
        }
    }.onStart { onStart() }.onCompletion { onCompletion() }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun deleteBejegyzes(
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: (String) -> Unit
    ) = flow {
        try {
            bejegyzesService.deleteBejegyzes(DeleteBejegyzesDto("hal"))
            val response = bejegyzesService.fetchBejegyzesList("hal")
            val frissBejegyzesek = response.map { b -> Bejegyzes(
                b.azonosito, b.felhasznaloAzonosito, b.datum, b.tartalom
            ) }
            bejegyzesDao.insertBejegyzesList(frissBejegyzesek)
            emit(frissBejegyzesek)
        } catch (e: Exception) {
            onError("Hálózati hiba történt")
        }
    }.onStart { onStart() }.onCompletion { onCompletion() }.flowOn(Dispatchers.IO)

    // TODO: megvalósítani a törlést
}