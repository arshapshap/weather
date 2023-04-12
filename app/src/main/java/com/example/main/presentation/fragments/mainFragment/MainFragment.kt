package com.example.main.presentation.fragments.mainFragment

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.main.R
import com.example.main.databinding.FragmentMainBinding
import com.example.main.di.appComponent
import com.example.main.di.lazyViewModel
import com.example.main.presentation.fragments.detailsBottomSheetFragment.DetailsBottomSheetFragment
import com.example.main.presentation.providers.PermissionsRequestProvider

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding by viewBinding(FragmentMainBinding::bind)
    private var permissionsRequestProvider: PermissionsRequestProvider? = null

    private val viewModel: MainViewModel by lazyViewModel {
        requireContext().appComponent().mainViewModel().create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent().inject(this)
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
            view, savedInstanceState
        )

        permissionsRequestProvider = PermissionsRequestProvider(activity as AppCompatActivity) {
            onLocationPermissionHandled(it)
        }

        subscribe()
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        permissionsRequestProvider = null
    }

    private fun subscribe() {
        with(viewModel) {
            weatherInfoLive.observe(viewLifecycleOwner) { info ->
                binding.cityNameTextView.text = info?.cityName ?: ""
                binding.temperatureTextView.text = if (info != null) getString(
                    R.string.temperature_in_celsius, info.temperatureInCelsius
                ) else ""
            }

            loadingLive.observe(viewLifecycleOwner) { isLoading ->
                binding.loadingProgressBar.isVisible = isLoading
                binding.cityNameTextView.isVisible = !isLoading
                binding.temperatureTextView.isVisible = !isLoading
            }

            viewModel.errorLive.observe(viewLifecycleOwner) { errorTextResourceId ->
                binding.weatherInfoContainer.isGone = errorTextResourceId != null
                errorTextResourceId?.let {
                    Toast.makeText(context, getString(it), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initViews() {
        with(binding) {
            getWeatherByCityNameButton.setOnClickListener {
                val cityName = cityNameEditText.text.toString()
                viewModel.getWeatherByCityName(cityName)
            }

            getWeatherByLocationButton.setOnClickListener {
                if (!checkLocationPermission()) {
                    requestLocationPermission()
                } else {
                    viewModel.getWeatherByLocation()
                }
            }

            weatherInfoContainer.setOnClickListener {
                if (viewModel.weatherInfoLive.value != null) {
                    showBottomSheetFragment()
                }
            }
        }
    }

    private fun checkLocationPermission() =
        permissionsRequestProvider?.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            ?: false

    private fun requestLocationPermission() {
        permissionsRequestProvider?.requestPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun shouldShowRationale() = ActivityCompat.shouldShowRequestPermissionRationale(
        requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION
    )

    private fun onLocationPermissionHandled(isGranted: Boolean) {
        if (isGranted) viewModel.getWeatherByLocation()
        else if (shouldShowRationale()) showAlertPermissionRequired {
            requestLocationPermission()
        }
        else showAlertPermissionRequired {
            openSettings()
        }

    }

    private fun openSettings() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts(
                "package", requireActivity().packageName, null
            )
        )
        startActivity(intent)
    }

    private fun showBottomSheetFragment() {
        childFragmentManager.beginTransaction().apply {
            add(
                DetailsBottomSheetFragment::class.java,
                bundleOf(DetailsBottomSheetFragment.WEATHER_INFO_KEY to viewModel.weatherInfoLive.value),
                DetailsBottomSheetFragment.TAG
            )
            commit()
        }
    }

    private fun showAlertPermissionRequired(buttonAction: (() -> Unit)) {
        AlertDialog.Builder(requireContext()).setTitle(R.string.location_permission_required)
            .setMessage(R.string.location_permission_required_message)
            .setPositiveButton(R.string.ok) { _, _ ->
                buttonAction.invoke()
            }.show()
    }
}