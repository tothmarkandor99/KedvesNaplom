package com.webtic.kedvesnaplom.network

import com.webtic.kedvesnaplom.network.dto.GetBejegyzesekListaElemDto
import com.webtic.kedvesnaplom.network.dto.PutBejegyzesDto
import retrofit2.Response
import retrofit2.http.*

interface BejegyzesService {

    @GET("bejegyzesek/{felhasznaloAzonosito}")
    suspend fun fetchBejegyzesList(@Path("felhasznaloAzonosito") felhasznaloAzonosito: String): List<GetBejegyzesekListaElemDto>

    @PUT("bejegyzes")
    suspend fun putBejegyzes(@Body bejegyzes: PutBejegyzesDto)

    @DELETE("bejegyzes/{felhasznaloAzonosito}")
    suspend fun deleteBejegyzes(@Path("felhasznaloAzonosito") felhasznaloAzonosito: String): Response<Unit>
}
