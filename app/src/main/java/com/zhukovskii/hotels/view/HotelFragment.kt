package com.zhukovskii.hotels.view

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.zhukovskii.hotels.viewmodel.HotelsViewModel
import com.zhukovskii.hotels.R
import com.zhukovskii.hotels.recycler.HotelRecyclerViewAdapter

class HotelFragment : Fragment() {

    private val viewModel: HotelsViewModel by activityViewModels()

    private lateinit var sortDistanceButton: Button
    private lateinit var sortSuitesButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hotel_list, container, false)

        with(view.findViewById<Button>(R.id.sort_distance_button)) {
            if (viewModel.sortByDistance) activate() else deactivate()
        }

        with(view.findViewById<Button>(R.id.sort_suites_button)) {
            if (viewModel.sortBySuites) activate() else deactivate()
        }

        with(view.findViewById<RecyclerView>(R.id.list)) {
            layoutManager = LinearLayoutManager(context)
            adapter = HotelRecyclerViewAdapter(
                viewModel.getHotelsSorted(),
                context,
                view,
                viewModel
            )
        }
        return view
    }

    /**
     * Redraw button as deactivated, i.e. corresponding sorting strategy is disabled
     */
    private fun Button.deactivate() {
        backgroundTintList = ColorStateList.valueOf(
            resources.getColor(R.color.white, context.theme)
        )
        setTextColor(resources.getColor(R.color.grey_accent, context.theme))
    }

    /**
     * Redraw button as activated, i.e. corresponding sorting strategy is applied
     */
    private fun Button.activate() {
        backgroundTintList = ColorStateList.valueOf(
            resources.getColor(R.color.cyan, context.theme)
        )
        setTextColor(resources.getColor(R.color.white, context.theme))
    }

    /**
     * Redraw the fragment after sorting strategy change
     */
    private fun refresh(view: View) {
        with(Navigation.findNavController(view)) {
            val id = currentDestination?.id!!
            popBackStack(id, true)
            navigate(id)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sortDistanceButton = view.findViewById(R.id.sort_distance_button)
        sortSuitesButton = view.findViewById(R.id.sort_suites_button)

        sortDistanceButton.setOnClickListener {
            with(viewModel) {
                sortByDistance = !sortByDistance
                if (sortByDistance and sortBySuites) sortBySuites = false
            }
            refresh(view)
        }

        sortSuitesButton.setOnClickListener {
            with(viewModel) {
                sortBySuites = !sortBySuites
                if (sortByDistance and sortBySuites) sortByDistance = false
            }
            refresh(view)
        }
    }
}