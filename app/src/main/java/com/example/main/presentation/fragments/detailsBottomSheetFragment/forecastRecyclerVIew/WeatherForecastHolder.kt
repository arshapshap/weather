package com.example.main.presentation.fragments.detailsBottomSheetFragment.forecastRecyclerVIew

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.main.R
import com.example.main.databinding.ItemWeatherForecastBinding
import com.example.main.domain.models.WeatherInfo
import java.text.SimpleDateFormat
import java.util.*

class WeatherForecastHolder(
    private val binding: ItemWeatherForecastBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(weatherInfo: WeatherInfo) {
        with(binding) {
            Glide.with(binding.root).load(weatherInfo.imageUrl).into(weatherImageView)

            temperatureTextView.text = itemView.context.getString(
                R.string.temperature_in_celsius,
                weatherInfo.temperatureInCelsius
            )
            dateTextView.text = weatherInfo.date?.let { SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault()).format(it) }
        }
    }
}