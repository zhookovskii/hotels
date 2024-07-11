package com.zhukovskii.hotels.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.zhukovskii.hotels.R
import com.zhukovskii.hotels.util.ImageLoader
import com.zhukovskii.hotels.viewmodel.HotelsViewModel

class HotelDetailsFragment : Fragment() {

    private val viewModel: HotelsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hotel_details, container, false)
        val details = viewModel.getHotelDetails()

        view.findViewById<TextView>(R.id.hotel_name).text = details.name
        view.findViewById<RatingBar>(R.id.hotel_rating).rating = details.stars
        view.findViewById<TextView>(R.id.hotel_address).text = details.address

        view.findViewById<TextView>(R.id.hotel_distance).text =
            getString(R.string.hotel_distance_format, details.distance)

        view.findViewById<TextView>(R.id.hotel_coordinates).text =
            getString(R.string.hotel_coordinates_format, details.lat, details.lon)

        with(view.findViewById<ImageView>(R.id.hotel_image)) {
            ImageLoader.load(context, details.image, this, cropPixels = 16)
        }

        if (details.suitesAvailable == 0) {
            view.findViewById<TextView>(R.id.available_suites_text).text =
                getString(R.string.no_suites_available)
            return view
        }

        val firstSuite = view.findViewById<TextView>(R.id.first_suite)
        val secondSuite = view.findViewById<TextView>(R.id.second_suite)
        val thirdSuite = view.findViewById<TextView>(R.id.third_suite)
        val moreSuites = view.findViewById<TextView>(R.id.more_suits)

        firstSuite.text = details.firstSuite ?: "".also { firstSuite.hide() }
        secondSuite.text = details.secondSuite ?: "".also { secondSuite.hide() }
        thirdSuite.text = details.thirdSuite ?: "".also { thirdSuite.hide() }

        moreSuites.text = if (details.moreSuites > 0) {
            getString(R.string.hotel_more_suites_format, details.moreSuites)
        } else {
            ""
        }

        return view
    }

    private fun TextView.hide() {
        visibility = View.GONE
    }
}