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
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _bejegyzesek = MutableStateFlow<List<Bejegyzes>>(listOf())
    val bejegyzesek: StateFlow<List<Bejegyzes>> = _bejegyzesek

    private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val _canAdd: MutableState<Boolean> = mutableStateOf(false)
    val canAdd: State<Boolean> get() = _canAdd

    suspend fun loadBejegyzesek(
        forceDownload: Boolean = false,
    ) {
        _bejegyzesek.value = mainRepository.loadBejegyzesek(forceDownload)
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val nowAsIso: String = df.format(Date())
        _canAdd.value = nowAsIso != _bejegyzesek.value[0].datum
    }

    fun refreshBejegyzesek(forceDownload: Boolean = false) {
        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.value = true
            loadBejegyzesek(forceDownload)
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