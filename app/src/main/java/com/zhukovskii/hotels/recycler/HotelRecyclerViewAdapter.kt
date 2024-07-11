package com.zhukovskii.hotels.recycler

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.navigation.Navigation
import com.zhukovskii.hotels.R

import com.zhukovskii.hotels.databinding.FragmentHotelBinding
import com.zhukovskii.hotels.model.Hotel
import com.zhukovskii.hotels.util.ImageLoader
import com.zhukovskii.hotels.viewmodel.HotelsViewModel

class HotelRecyclerViewAdapter(
    private val hotels: List<Hotel>,
    private val context: Context,
    private val view: View,
    private val viewModel: HotelsViewModel
) : RecyclerView.Adapter<HotelRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentHotelBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hotel = hotels[position]
        holder.bind(hotel)
    }

    override fun getItemCount(): Int = hotels.size

    inner class ViewHolder(
        private val binding: FragmentHotelBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val hotelImage: ImageView = binding.hotelImage
        private val hotelName: TextView = binding.hotelName
        private val hotelAddress: TextView = binding.hotelAddress
        private val hotelDistance: TextView = binding.hotelDistance
        private val hotelRating: RatingBar = binding.hotelRating

        fun bind(hotel: Hotel) {
            hotelName.text = hotel.name
            hotelAddress.text = hotel.address
            hotelDistance.text = context.getString(R.string.hotel_distance_format, hotel.distance)
            hotelRating.rating = hotel.stars

            ImageLoader.load(context, hotel.image, hotelImage, cropPixels = 8)

            binding.root.setOnClickListener {
                viewModel.focusHotelId = hotel.id
                Navigation.findNavController(view).navigate(R.id.to_hotel_details_fragment)
            }
        }
    }

}