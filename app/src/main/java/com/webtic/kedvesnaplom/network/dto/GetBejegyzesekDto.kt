package com.webtic.kedvesnaplom.network.dto

data class GetBejegyzesekDto(
    val azonosito : Int,
    val felhasznaloAzonosito: String,
    val datum: String,
    val tartalom: String,
)