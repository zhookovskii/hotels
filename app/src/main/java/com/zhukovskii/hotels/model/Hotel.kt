package com.zhukovskii.hotels.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Hotel(
    val id: Int,
    val name: String,
    val address: String,
    val stars: Float,
    val distance: Float,
    val image: String? = null,
    val suites_availability: String,
    val lat: Float,
    val lon: Float
) {
    fun availableSuites(): Int {
        return suites_availability.split(':').size
    }
}