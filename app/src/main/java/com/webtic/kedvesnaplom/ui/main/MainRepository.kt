package com.webtic.kedvesnaplom.ui.main

import androidx.annotation.WorkerThread
import com.webtic.kedvesnaplom.model.Bejegyzes
import com.webtic.kedvesnaplom.network.BejegyzesService
import com.webtic.kedvesnaplom.persistence.BejegyzesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
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
            // TODO: api hívás, bejegyzések perzisztálása a DAO-n keresztül
        } else {
            emit(bejegyzesek)
        }
    }.onStart { onStart() }.onCompletion { onCompletion() }.flowOn(Dispatchers.IO)

    // TODO: megvalósítani a törlést
}