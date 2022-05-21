package com.webtic.kedvesnaplom.ui.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.webtic.kedvesnaplom.model.Bejegyzes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _bejegyzesek = MutableStateFlow<List<Bejegyzes>>(listOf())
    val bejegyzesek: StateFlow<List<Bejegyzes>> = _bejegyzesek

    private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    suspend fun loadBejegyzesek(
        forceDownload: Boolean = false,
    ) {
        _bejegyzesek.value = mainRepository.loadBejegyzesek(forceDownload)
    }

    fun refreshBejegyzesek(forceDownload: Boolean = false) {
        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.value = true
            loadBejegyzesek(true)
            _isLoading.value = false
        }
    }

    fun deleteBejegyzes() {
        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.value = true
            mainRepository.deleteBejegyzes()
            loadBejegyzesek(forceDownload = true)
            _isLoading.value = false
        }
    }
}