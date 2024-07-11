package com.zhukovskii.hotels.model

import retrofit2.http.GET
import retrofit2.http.Path

interface HotelsApi {

    @GET("0777.json")
    suspend fun getHotels(): List<HotelListItem>

    @GET("{id}.json")
    suspend fun getHotel(@Path("id") id: Int): Hotel
}