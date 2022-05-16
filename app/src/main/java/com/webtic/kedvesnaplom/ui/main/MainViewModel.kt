package com.webtic.kedvesnaplom.ui.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.webtic.kedvesnaplom.model.Bejegyzes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    mainRepository: MainRepository
) : ViewModel() {

    val bejegyzesList: Flow<List<Bejegyzes>> =
        mainRepository.loadBejegyzesek(
            onStart = { _isLoading.value = true },
            onCompletion = { _isLoading.value = false },
            onError = { errorMsg -> Log.d("KN", errorMsg) }
        )

    private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading
}