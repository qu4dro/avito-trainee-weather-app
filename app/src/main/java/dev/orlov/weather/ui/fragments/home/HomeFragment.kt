package dev.orlov.weather.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dev.orlov.weather.R
import dev.orlov.weather.databinding.FragmentHomeBinding
import dev.orlov.weather.databinding.TodayOverviewBinding
import dev.orlov.weather.domain.model.Weather
import dev.orlov.weather.utils.LoadState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    val binding
        get() = _binding!!

    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    if (uiState.isLocationSelected) {

                    }
                    when (uiState.loadState) {
                        LoadState.LOADING -> {
                            setLoadingUi()
                        }
                        LoadState.ERROR -> {
                            setErrorUi()
                        }
                        LoadState.SUCCESS -> {
                            uiState.weather?.let {
                                setSuccessUi(it)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setUi() {
        binding.apply {
            swipeRefresh.setOnRefreshListener {
                viewModel.getForecast()
            }
        }
    }

    private fun setLoadingUi() {
        binding.apply {
            swipeRefresh.isRefreshing = true
        }
    }

    private fun setErrorUi() {
        binding.apply {
            swipeRefresh.isRefreshing = false
        }
    }

    private fun setSuccessUi(weather: Weather) {
        binding.apply {
            mainGroup.visibility = View.VISIBLE
            swipeRefresh.isRefreshing = false
            currentWeather.apply {
                tvCurrentTemp.text = weather.current.temp.toString()
                tvFeelsLike.text = weather.current.feelsLike.toString()
                tvWeatherType.text = weather.current.condition.text
            }
            todayOverview.apply {
                with(weather.forecast[0]) {
                    tvMaxTempValue.text = this.maxTemp.toString()
                    tvMinTempValue.text = this.minTemp.toString()
                    tvSunriseValue.text = this.sunrise
                    tvSunsetValue.text = this.sunset
                }
                tvWindValue.text = weather.current.wind.toString()
                tvHumidityValue.text = weather.current.humidity.toString()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}