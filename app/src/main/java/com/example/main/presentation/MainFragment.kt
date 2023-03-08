package com.example.main.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.main.R
import com.example.main.databinding.FragmentMainBinding
import com.example.main.domain.models.WeatherInfo
import com.example.main.domain.usecases.GetWeatherByCityNameUseCase

class MainFragment : Fragment(R.layout.fragment_main) {
    private var binding: FragmentMainBinding? = null
    private lateinit var vm: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        vm = ViewModelProvider(this)[MainViewModel::class.java]

        vm.weatherInfoLive.observe(viewLifecycleOwner) {
            binding?.run {
                cityNameTextView.text = it.cityName
                temperatureTextView.text =
                    getString(R.string.temperature_in_celsius, it.temperatureInCelsius)
            }
        }

        binding?.run {
            getWeatherByCityNameButton.setOnClickListener {
                val cityName = cityNameEditText.text.toString()
                vm.getWeatherByCityName(cityName)
            }

            getWeatherByLocationButton.setOnClickListener {
                val latitude = 1
                val longitude = 2
                vm.getWeatherByLocation(latitude, longitude)
            }

            weatherInfoView.setOnClickListener {
                if (vm.weatherInfoLive.value == null) {
                    Toast
                        .makeText(context, getString(R.string.no_data_uploaded), Toast.LENGTH_SHORT)
                        .show()
                } else {
                    childFragmentManager.beginTransaction().apply {
                        add(
                            DetailedBottomSheetFragment::class.java,
                            bundleOf(DetailedBottomSheetFragment.WEATHER_INFO_KEY to vm.weatherInfoLive.value),
                            DetailedBottomSheetFragment.TAG
                        )
                        commit()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}