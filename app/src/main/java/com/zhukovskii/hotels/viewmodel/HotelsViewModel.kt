package com.zhukovskii.hotels.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.zhukovskii.hotels.model.Hotel
import com.zhukovskii.hotels.model.HotelsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HotelsViewModel @Inject constructor(
    private val api: HotelsApi
) : ViewModel() {

    /**
     * Set this flag to indicate that hotels should be sorted by distance from city center
     */
    var sortByDistance = false

    /**
     * Set this flag to indicate that hotels should be sorted by number of available suites
     */
    var sortBySuites = false

    /**
     * Id of the last hotel that user selected
     */
    var focusHotelId = 0

    /**
     * Retrieve hotels from API
     */
    val hotels: LiveData<HotelsWithLoadState> = liveData {
        while (true) {
            emit(
                HotelsWithLoadState(LoadState.LOADING)
            )
            delay(2000)
            try {
                val lst = api.getHotels().map { api.getHotel(it.id) }
                emit(
                    HotelsWithLoadState(LoadState.SUCCESS, lst)
                )
                break
            } catch (e: IOException) {
                Log.i("ViewModel", e.stackTraceToString())
                emit(
                    HotelsWithLoadState(LoadState.ERROR)
                )
            }
        }
    }

    /**
     * Get hotels according to set sorting strategy
     */
    fun getHotelsSorted(): List<Hotel> {
        val lst = hotels.value?.hotels ?: return emptyList()
        if (sortByDistance) return lst.sortedBy { it.distance }
        if (sortBySuites) return lst.sortedByDescending { it.availableSuites() }
        return lst
    }

    /**
     * Get details for hotel with the current focus id
     */
    fun getHotelDetails(): HotelDetails {
        val hotel = hotels.value?.hotels?.find { it.id == focusHotelId }!!

        val suites = hotel.suites_availability
            .split(':')
            .filter { it.isNotBlank() }

        return HotelDetails(
            name = hotel.name,
            address = hotel.address,
            stars = hotel.stars,
            distance = hotel.distance,
            image = hotel.image,
            lat = hotel.lat,
            lon = hotel.lon,
            suitesAvailable = suites.size,
            firstSuite = suites.getOrNull(0),
            secondSuite = suites.getOrNull(1),
            thirdSuite = suites.getOrNull(2),
            moreSuites = maxOf(0, suites.size - 3)
        )

    }

    data class HotelDetails(
        val name: String,
        val address: String,
        val stars: Float,
        val distance: Float,
        val image: String?,
        val lat: Float,
        val lon: Float,
        val suitesAvailable: Int,
        val firstSuite: String?,
        val secondSuite: String?,
        val thirdSuite: String?,
        val moreSuites: Int
    )
}