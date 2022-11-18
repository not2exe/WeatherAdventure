package com.weatheradventure.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatheradventure.R
import com.weatheradventure.di.WeatherApp
import com.weatheradventure.domain.ioc.SearchFragmentComponent
import com.weatheradventure.domain.ioc.SearchFragmentViewComponent
import javax.inject.Inject


class SearchLocationsFragment : Fragment() {
    private lateinit var fragmentComponent: SearchFragmentComponent
    private var fragmentViewComponent: SearchFragmentViewComponent? = null

    @Inject
    lateinit var viewController: SearchLocationsViewController

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentComponent =
            (context?.applicationContext as WeatherApp).appComponent.activityComponent()
                .searchFragmentComponentFactory().create(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_search_locations, container, false)
        fragmentViewComponent =
            fragmentComponent.searchFragmentViewComponentFactory().create(view, viewLifecycleOwner)
                .apply {
                    inject(this@SearchLocationsFragment)
                    viewController.setupViews()
                }
        return view
    }

    override fun onDestroyView() {
        fragmentViewComponent = null
        super.onDestroyView()
    }


}