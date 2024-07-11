package com.zhukovskii.hotels.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HotelListItem(
    val id: Int,
    val name: String,
    val address: String,
    val stars: Float,
    val distance: Float,
    val suites_availability: String
)