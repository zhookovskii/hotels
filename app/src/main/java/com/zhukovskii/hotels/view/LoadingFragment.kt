package com.zhukovskii.hotels.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.zhukovskii.hotels.R
import com.zhukovskii.hotels.viewmodel.HotelsViewModel
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.zhukovskii.hotels.viewmodel.HotelsWithLoadState
import com.zhukovskii.hotels.viewmodel.LoadState

class LoadingFragment : Fragment() {

    private val viewModel: HotelsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_loading, container, false)

        val hotelsObserver = Observer<HotelsWithLoadState> {
            when (it.loadState) {
                LoadState.ERROR ->
                    Navigation.findNavController(view).navigate(R.id.to_no_network_fragment)
                LoadState.SUCCESS ->
                    Navigation.findNavController(view).navigate(R.id.to_hotel_fragment)
                LoadState.LOADING -> {}
            }
        }

        viewModel.hotels.observe(viewLifecycleOwner, hotelsObserver)

        return view
    }
}
