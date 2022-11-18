package com.weatheradventure.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatheradventure.R
import com.weatheradventure.di.WeatherApp
import com.weatheradventure.domain.ioc.WeatherFragmentComponent
import com.weatheradventure.domain.ioc.WeatherFragmentViewComponent
import javax.inject.Inject


class WeatherFragment : Fragment() {
    private lateinit var fragmentComponent: WeatherFragmentComponent
    private var fragmentViewComponent: WeatherFragmentViewComponent? = null

    @Inject
    lateinit var viewController: WeatherViewController

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentComponent =
            (context?.applicationContext as WeatherApp).appComponent.activityComponent()
                .weatherFragmentComponentFactory().create(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)
        fragmentViewComponent =
            fragmentComponent.weatherFragmentViewComponentFactory().create(view, viewLifecycleOwner)
                .apply {
                    inject(this@WeatherFragment)
                    viewController.setupViews()
                }
        return view
    }

    override fun onDestroyView() {
        fragmentViewComponent = null
        super.onDestroyView()
    }

}