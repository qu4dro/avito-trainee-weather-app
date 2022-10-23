package dev.orlov.weather.ui.fragments.started

import android.Manifest
import android.content.Context
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dev.orlov.weather.R
import dev.orlov.weather.databinding.FragmentStartedBinding
import dev.orlov.weather.domain.model.City
import dev.orlov.weather.ui.adapters.CityAdapter
import dev.orlov.weather.utils.Coordinates
import dev.orlov.weather.utils.LoadState
import dev.orlov.weather.utils.LocationUtility
import dev.orlov.weather.utils.REQUEST_CODE_LOCATION_PERMISSION
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber

class StartedFragment : Fragment(R.layout.fragment_started), EasyPermissions.PermissionCallbacks {

    private var _binding: FragmentStartedBinding? = null
    val binding
        get() = _binding!!

    private val viewModel: StartedViewModel by activityViewModels()
    private val adapter: CityAdapter = CityAdapter(object : CityAdapter.OnItemClickListener {
        override fun onCityClick(city: City) {
            viewModel.selectCity(city.copy(isMain = true))
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        getCurrentLocation()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    Timber.d(uiState.toString())
                    if (uiState.isCitySelected && uiState.isPermissionsGranted) {
                        findNavController().navigate(R.id.action_startedFragment_to_homeFragment)
                    }
                    uiState.loadState?.let {
                        when (uiState.loadState) {
                            LoadState.LOADING -> {}
                            LoadState.ERROR -> {}
                            LoadState.SUCCESS -> {
                                adapter.submitList(uiState.cities)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupUi() {
        binding.apply {
            rvCities.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getCurrentLocation() {
        checkGpsIsEnabled()
        requestPermissionsAndLocationUpdates()
    }

    private fun requestLocationUpdates() {
        viewModel.locationLiveData.apply {
            observe(viewLifecycleOwner, object : Observer<Coordinates> {
                override fun onChanged(t: Coordinates?) {
                    value?.let {
                        setUserLocation(it)
                    }
                    removeObserver(this)
                }
            })
        }
    }

    private fun setUserLocation(coordinates: Coordinates) {
        val cityName = LocationUtility.getCityString(
            coordinates.lat,
            coordinates.lng,
            requireContext()
        )
        viewModel.searchCity(cityName)
    }

    private fun requestPermissionsAndLocationUpdates() {
        if (LocationUtility.hasLocationPermissions(requireContext())) {
            viewModel.updatedPermissionsStatus(true)
            requestLocationUpdates()
            return
        } else {
            viewModel.updatedPermissionsStatus(false)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                EasyPermissions.requestPermissions(
                    this,
                    "You need to accept location permissions",
                    REQUEST_CODE_LOCATION_PERMISSION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } else {
                EasyPermissions.requestPermissions(
                    this,
                    "You need to accept location permissions",
                    REQUEST_CODE_LOCATION_PERMISSION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                    //place for code for background location
                )
            }
        }
    }

    private fun checkGpsIsEnabled(): Boolean {
        val lm: LocationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            viewModel.updateGpsStatus(lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
        } catch (ex: Exception) {
        }
        if (!viewModel.uiState.value.isGpsEnabled) {
            return false
        }
        return true
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        viewModel.updatedPermissionsStatus(true)
        requestLocationUpdates()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissionsAndLocationUpdates()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

}