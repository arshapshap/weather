package com.example.main.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.example.main.databinding.ItemWeatherParameterBinding
import com.example.main.presentation.recyclerview.WeatherParameter

class WeatherParametersHolder(
    private val binding: ItemWeatherParameterBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(weatherParameter: WeatherParameter) {
        with (binding) {
            parameterNameTextView.text = weatherParameter.name
            valueTextView.text = weatherParameter.value
        }
    }
}