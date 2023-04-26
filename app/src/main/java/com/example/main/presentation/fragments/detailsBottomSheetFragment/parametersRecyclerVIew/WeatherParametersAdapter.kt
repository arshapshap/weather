package com.example.main.presentation.fragments.detailsBottomSheetFragment.parametersRecyclerVIew

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.main.databinding.ItemWeatherParameterBinding

class WeatherParametersAdapter(
    private val list: List<WeatherParameter>,
) : RecyclerView.Adapter<WeatherParametersHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherParametersHolder = WeatherParametersHolder(
        ItemWeatherParameterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(
        weatherParametersHolder: WeatherParametersHolder,
        position: Int
    ) {
        weatherParametersHolder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}