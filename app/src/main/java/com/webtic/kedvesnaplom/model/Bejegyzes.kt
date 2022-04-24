package com.webtic.kedvesnaplom.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
@Immutable
data class Bejegyzes (
    @PrimaryKey val azonosito: Int,
    val felhasznaloAzonosito: String,
    val datum: String,
    val tartalom: String,
)