package com.webtic.kedvesnaplom.network

import com.webtic.kedvesnaplom.model.Bejegyzes
import com.webtic.kedvesnaplom.network.dto.DeleteBejegyzes
import com.webtic.kedvesnaplom.network.dto.GetBejegyzesek
import com.webtic.kedvesnaplom.network.dto.PutBejegyzes
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT

interface BejegyzesService {

    @GET("bejegyzesek")
    suspend fun fetchBejegyzesList(): List<GetBejegyzesek>

    @PUT("bejegyzes")
    suspend fun putBejegyzes(bejegyzes: PutBejegyzes)

    @DELETE("bejegyzes")
    suspend fun putBejegyzes(bejegyzes: DeleteBejegyzes)
}