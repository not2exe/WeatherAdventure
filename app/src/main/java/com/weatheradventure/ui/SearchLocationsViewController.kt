package com.weatheradventure.ui

import android.view.View
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import androidx.annotation.StringRes
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.findNavController
import com.example.weatheradventure.R
import com.example.weatheradventure.databinding.FragmentSearchLocationsBinding
import com.google.android.material.snackbar.Snackbar
import com.weatheradventure.data.remoteWeather.StateOfResponse
import com.weatheradventure.domain.MyFactory
import com.weatheradventure.ui.stateholders.SearchLocationsViewModel
import javax.inject.Inject

class SearchLocationsViewController @Inject constructor(
    private val binding: FragmentSearchLocationsBinding,
    private val fragment: SearchLocationsFragment,
    private val viewModelFactory: SearchLocationsViewModel.Factory,
    private val viewLifecycleOwner: LifecycleOwner
) {
    private val context = binding.root.context
    private val viewModel by fragment.viewModels<SearchLocationsViewModel> {
        MyFactory(
            fragment
        ) { handle: SavedStateHandle ->
            viewModelFactory.create(handle)
        }
    }
    private val adapter = ArrayAdapter(
        context,
        android.R.layout.simple_dropdown_item_1line, arrayListOf<String>()
    )

    fun setupViews() {
        fragment.requireActivity()
            .findViewById<FrameLayout>(R.id.navigate_layout).visibility = View.GONE
        setupObservers()
        setupAutoCompleteText()
        setupButtons()
    }

    private fun setupObservers() {
        viewModel.stateOfResponse.observe(viewLifecycleOwner) {
            viewModel.stateOfResponse.observe(viewLifecycleOwner) { state ->
                val id = when (state) {
                    StateOfResponse.SERVER_ERROR -> R.string.server_error
                    StateOfResponse.TIMEOUT -> R.string.timeout
                    StateOfResponse.CLIENT_ERROR -> R.string.client_error
                    StateOfResponse.CONNECTION_ERROR -> R.string.connection_error
                    StateOfResponse.FAILURE -> R.string.something_went_wrong
                    StateOfResponse.SUCCESS -> null
                }
                showSnackbar(id)
            }
        }
        viewModel.predicts.observe(viewLifecycleOwner) { predicts ->
            adapter.clear()
            adapter.addAll(predicts.map { it.formatted })
            adapter.notifyDataSetChanged()
        }
    }

    private fun setupAutoCompleteText() = with(binding) {
        autoCompleteTV.setAdapter(adapter)
        //public void setThreshold (int threshold)
        //Specifies the minimum number of characters the user has to type in the edit box before the drop down list is shown.
        autoCompleteTV.threshold = 1
        autoCompleteTV.addTextChangedListener { text ->
            viewModel.getPredicts(text.toString())
        }


    }

    private fun setupButtons() = with(binding) {
        checkButton.setOnClickListener {
            viewModel.selectPlace(autoCompleteTV.text.toString())
            fragment.findNavController().popBackStack()
        }
        backButton.setOnClickListener {
            fragment.findNavController().popBackStack()
        }
    }

    private fun showSnackbar(@StringRes id: Int?) {
        if (id == null) return
        Snackbar.make(
            fragment.requireActivity().findViewById(android.R.id.content),
            id,
            Snackbar.LENGTH_LONG
        ).show()
    }
}