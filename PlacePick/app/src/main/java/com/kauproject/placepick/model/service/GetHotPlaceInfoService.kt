package com.kauproject.placepick.model.service

import com.kauproject.placepick.model.response.GetHotPlaceInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

private const val API_KEY = "6c64484f516b6b6d36385561735152"

interface GetHotPlaceInfoService {
    @GET("$API_KEY/json/citydata_ppltn/1/10/{hotPlace}")
    suspend fun getHotPlaceInfo(
        @Path("hotPlace") hotPlace: String
    ): Response<GetHotPlaceInfoResponse>
}