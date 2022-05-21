package com.webtic.kedvesnaplom.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.webtic.kedvesnaplom.model.Bejegyzes
import com.webtic.kedvesnaplom.network.dto.PutBejegyzesDto
import com.webtic.kedvesnaplom.ui.main.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository,
    private val mainRepository: MainRepository,
) : ViewModel() {
    private val _bejegyzes = MutableStateFlow<Bejegyzes?>(null)
    val bejegyzes: StateFlow<Bejegyzes?> = _bejegyzes

    fun loadBejegyzes(azonosito: Int?) {
        if (azonosito === null) {
            val df: DateFormat =
                SimpleDateFormat("yyyy-MM-dd")
            val nowAsIso: String = df.format(Date())

            _bejegyzes.value = Bejegyzes(azonosito = -1,"hal", nowAsIso, tartalom = "")
        } else {
            viewModelScope.launch {
                _bejegyzes.value = detailsRepository.getBejegyzes(azonosito)
            }
        }
    }

    fun saveBejegyzes(tartalom: String) {
        val b = _bejegyzes.value
        if (b != null) {
            viewModelScope.launch {
                detailsRepository.putBejegyzes(PutBejegyzesDto(b.felhasznaloAzonosito, tartalom))
                mainRepository.loadBejegyzesek(true)
            }
        }
    }
}