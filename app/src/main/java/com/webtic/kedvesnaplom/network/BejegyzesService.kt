package com.webtic.kedvesnaplom.network

import com.webtic.kedvesnaplom.network.dto.DeleteBejegyzesDto
import com.webtic.kedvesnaplom.network.dto.GetBejegyzesekListaElemDto
import com.webtic.kedvesnaplom.network.dto.PutBejegyzesDto
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT

interface BejegyzesService {

    @GET("bejegyzesek")
    suspend fun fetchBejegyzesList(): Call<List<GetBejegyzesekListaElemDto>>

    @PUT("bejegyzes")
    suspend fun putBejegyzes(bejegyzes: PutBejegyzesDto)

    @DELETE("bejegyzes")
    suspend fun putBejegyzes(bejegyzes: DeleteBejegyzesDto)
}