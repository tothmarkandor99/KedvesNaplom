package com.webtic.kedvesnaplom.ui.details

import androidx.annotation.WorkerThread
import com.webtic.kedvesnaplom.persistence.BejegyzesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val bejegyzesDao: BejegyzesDao
) {

    @WorkerThread
    fun getBejegyzesById(id: Int) = flow {
        val bejegyzes = bejegyzesDao.getBejegyzes(id)
        emit(bejegyzes)
    }.flowOn(Dispatchers.IO)

    // TODO: megvalósítani a hozzáadást/mentést
}