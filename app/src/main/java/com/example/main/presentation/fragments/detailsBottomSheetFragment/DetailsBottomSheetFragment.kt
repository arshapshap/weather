package com.example.main.presentation.fragments.detailsBottomSheetFragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.main.R
import com.example.main.databinding.FragmentBottomSheetDetailsBinding
import com.example.main.di.appComponent
import com.example.main.di.lazyViewModel
import com.example.main.domain.models.WeatherInfo
import com.example.main.presentation.fragments.detailsBottomSheetFragment.forecastRecyclerVIew.WeatherForecastAdapter
import com.example.main.presentation.fragments.detailsBottomSheetFragment.parametersRecyclerVIew.WeatherParameter
import com.example.main.presentation.fragments.detailsBottomSheetFragment.parametersRecyclerVIew.WeatherParametersAdapter
import com.example.main.presentation.utils.getSerializableCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DetailsBottomSheetFragment :
    BottomSheetDialogFragment(R.layout.fragment_bottom_sheet_details) {

    private val binding by viewBinding(FragmentBottomSheetDetailsBinding::bind)
    private lateinit var weatherInfo: WeatherInfo

    private val viewModel: DetailsViewModel by lazyViewModel {
        requireContext().appComponent().detailsViewModel().create(weatherInfo.locationInfo)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (dialog as? BottomSheetDialog)?.behavior?.state = STATE_EXPANDED


        weatherInfo = arguments?.getSerializableCompat(WEATHER_INFO_KEY) ?: throw IllegalArgumentException()

        initViews(weatherInfo)

        subscribe()
        viewModel.loadForecast()

    }

    private fun initViews(weatherInfo: WeatherInfo) {
        val parameters = getWeatherParameters(weatherInfo)
        with(binding) {
            Glide
                .with(binding.root)
                .load(weatherInfo.imageUrl)
                .into(weatherImageView)

            infoRecyclerView.adapter = WeatherParametersAdapter(parameters)
            forecastRecyclerView.adapter = WeatherForecastAdapter(listOf())

            closeButton.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun subscribe() {
        viewModel.weatherForecastLive.observe(viewLifecycleOwner) { forecast ->
            forecast?.let {
                binding.forecastRecyclerView.adapter = WeatherForecastAdapter(it.list)
                binding.forecastRecyclerView.invalidate()
            }
        }
        viewModel.loadingLive.observe(viewLifecycleOwner) { isLoading ->
            binding.forecastRecyclerView.isVisible = !isLoading
            binding.loadingProgressBar.isVisible = isLoading
        }
        viewModel.errorLive.observe(viewLifecycleOwner) { errorTextResourceId ->
            binding.forecastContainer.isGone = errorTextResourceId != null
            errorTextResourceId?.let {
                Toast.makeText(context, getString(it), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getWeatherParameters(weatherInfo: WeatherInfo): ArrayList<WeatherParameter> =
        arrayListOf(
            WeatherParameter(
                getString(R.string.city), weatherInfo.cityName
            ),
            WeatherParameter(
                getString(R.string.temperature), getString(
                    R.string.temperature_in_celsius, weatherInfo.temperatureInCelsius
                )
            ),
            WeatherParameter(
                getString(R.string.humidity), getString(
                    R.string.humidity_as_percentage, weatherInfo.humidityAsPercentage
                )
            ),
            WeatherParameter(
                getString(R.string.pressure), getString(
                    R.string.pressure_in_millimeters_of_mercury,
                    weatherInfo.pressureInMillimetersOfMercury
                )
            ),
            WeatherParameter(
                getString(R.string.wind_speed), getString(
                    R.string.wind_speed_in_meters_per_second, weatherInfo.windSpeedInMetersPerSecond
                )
            ),
        )

    companion object {

        const val WEATHER_INFO_KEY = "WEATHER_INFO_KEY"
        const val TAG = "DETAILED_WEATHER_INFO"
    }
}