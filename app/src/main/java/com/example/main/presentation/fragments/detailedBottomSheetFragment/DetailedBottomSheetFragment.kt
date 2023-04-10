package com.example.main.presentation.fragments.detailedBottomSheetFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.main.R
import com.example.main.databinding.FragmentBottomSheetDetailsBinding
import com.example.main.domain.models.WeatherInfo
import com.example.main.presentation.fragments.detailedBottomSheetFragment.recyclerview.WeatherParameter
import com.example.main.recyclerview.WeatherParametersAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DetailedBottomSheetFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentBottomSheetDetailsBinding.inflate(inflater)
        val weatherInfo = arguments?.getSerializable(WEATHER_INFO_KEY) as WeatherInfo
        val parameters = getWeatherParameters(weatherInfo)

        with(binding) {
            Glide
                .with(binding.root)
                .load(weatherInfo.imageUrl)
                .into(weatherImageView)

            infoRecyclerView.adapter = WeatherParametersAdapter(parameters)
            infoRecyclerView.layoutManager = LinearLayoutManager(context)
            closeButton.setOnClickListener {
                dismiss()
            }
        }
        return binding.root
    }

    private fun getWeatherParameters(weatherInfo: WeatherInfo): ArrayList<WeatherParameter> =
        arrayListOf(
            WeatherParameter("City", weatherInfo.cityName),
            WeatherParameter(
                "Temperature",
                getString(R.string.temperature_in_celsius, weatherInfo.temperatureInCelsius)
            ),
            WeatherParameter(
                "Humidity",
                getString(R.string.humidity_as_percentage, weatherInfo.humidityAsPercentage)
            ),
            WeatherParameter(
                "Pressure",
                getString(
                    R.string.pressure_in_millimeters_of_mercury,
                    weatherInfo.pressureInMillimetersOfMercury
                )
            ),
            WeatherParameter(
                "Wind speed",
                getString(
                    R.string.wind_speed_in_meters_per_second,
                    weatherInfo.windSpeedInMetersPerSecond
                )
            ),
        )

    companion object {
        const val WEATHER_INFO_KEY = "WEATHER_INFO_KEY"
        const val TAG = "DETAILED_WEATHER_INFO"
    }
}