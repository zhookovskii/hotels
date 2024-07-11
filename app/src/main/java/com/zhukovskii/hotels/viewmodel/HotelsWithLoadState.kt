package com.zhukovskii.hotels.viewmodel

import com.zhukovskii.hotels.model.Hotel

/**
 * Wrapper class for hotels retrieved from API
 *
 * Load state is used to indicate to the UI whether another attempt
 * to retrieve hotels is in process (`LoadState.LOADING`), successful
 * (`LoadState.SUCCESS`) or unsuccessful (`LoadState.ERROR`)
 */
data class HotelsWithLoadState(
    val loadState: LoadState,
    val hotels: List<Hotel>? = null
)