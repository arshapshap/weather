package com.example.main.presentation.fragments.mainFragment

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.main.R
import com.example.main.databinding.FragmentMainBinding
import com.example.main.presentation.fragments.detailedBottomSheetFragment.DetailedBottomSheetFragment
import com.example.main.presentation.providers.PermissionsRequestProvider

class MainFragment : Fragment(R.layout.fragment_main) {
    private var binding: FragmentMainBinding? = null
    private var permissionsRequestProvider: PermissionsRequestProvider? = null
    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        permissionsRequestProvider =
            PermissionsRequestProvider(activity as AppCompatActivity) {
                onLocationPermissionHandled(it)
            }

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(activity as Activity)
        )[MainViewModel::class.java]

        viewModel.weatherInfoLive.observe(viewLifecycleOwner) { info ->
            binding?.cityNameTextView?.text =
                info?.cityName ?: getString(R.string.no_data_uploaded)
            binding?.temperatureTextView?.text =
                if (info != null) getString(
                    R.string.temperature_in_celsius,
                    info.temperatureInCelsius
                ) else ""
        }

        viewModel.loadingLive.observe(viewLifecycleOwner) { isLoading ->
            binding?.loadingProgressBar?.visibility =
                if (isLoading) View.VISIBLE else View.INVISIBLE
            binding?.cityNameTextView?.visibility =
                if (!isLoading) View.VISIBLE else View.INVISIBLE
            binding?.temperatureTextView?.visibility =
                if (!isLoading) View.VISIBLE else View.INVISIBLE
        }

        binding?.run {
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

            weatherInfoView.setOnClickListener {
                if (viewModel.weatherInfoLive.value != null) {
                    showBottomSheetFragment()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        permissionsRequestProvider = null
    }

    private fun checkLocationPermission() =
        permissionsRequestProvider?.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            ?: false

    private fun requestLocationPermission() {
        permissionsRequestProvider?.requestPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun shouldShowRationale() =
        ActivityCompat.shouldShowRequestPermissionRationale(
            requireActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    private fun onLocationPermissionHandled(isGranted: Boolean) {
        if (isGranted)
            viewModel.getWeatherByLocation()
        else if (!isGranted && shouldShowRationale())
            showAlertPermissionRequired {
                requestLocationPermission()
            }
        else if (!isGranted)
            showAlertPermissionRequired {
                openSettings()
            }

    }

    private fun openSettings() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", requireActivity().packageName, null)
        )
        startActivity(intent)
    }

    private fun showBottomSheetFragment() {
        childFragmentManager.beginTransaction().apply {
            add(
                DetailedBottomSheetFragment::class.java,
                bundleOf(DetailedBottomSheetFragment.WEATHER_INFO_KEY to viewModel.weatherInfoLive.value),
                DetailedBottomSheetFragment.TAG
            )
            commit()
        }
    }

    private fun showAlertPermissionRequired(buttonAction: (() -> Unit)) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.location_permission_required)
            .setMessage(R.string.location_permission_required_message)
            .setPositiveButton(R.string.ok) { _, _ ->
                buttonAction.invoke()
            }
            .show()
    }
}