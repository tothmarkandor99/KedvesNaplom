package com.webtic.kedvesnaplom.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository
) : ViewModel() {

    private val bejegyzesIdSharedFlow: MutableSharedFlow<Long> = MutableSharedFlow(replay = 1)

    init {
        Log.d("KN","init DetailViewModel")
    }

    fun loadBejegyzesById(id: Long) = bejegyzesIdSharedFlow.tryEmit(id)

}