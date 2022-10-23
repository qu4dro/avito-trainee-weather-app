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
import androidx.navigation.fragment.findNavController
import coil.load
import dev.orlov.weather.R
import dev.orlov.weather.databinding.FragmentHomeBinding
import dev.orlov.weather.domain.model.Weather
import dev.orlov.weather.ui.adapters.HourAdapter
import dev.orlov.weather.utils.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    val binding
        get() = _binding!!

    private val hourAdapter = HourAdapter()

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
        setUi()
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.map { it.selectedCity }.distinctUntilChanged().collectLatest {
                    viewModel.getForecast()
                    binding.tvCity.text = it?.name
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { uiState ->
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
            rvHourly.adapter = hourAdapter
            swipeRefresh.setOnRefreshListener {
                viewModel.getForecast()
            }
            ivSearch.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
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
            setCurrent(weather)
            setToday(weather)
            setTomorrow(weather)
        }
    }

    private fun setCurrent(weather: Weather) {
        binding.currentWeather.apply {
            tvCurrentTemp.text = weather.current.temp.toCelsiusString()
            tvFeelsLike.text =
                getString(R.string.feels_like, weather.current.feelsLike.toCelsiusString())
            tvWeatherType.text = weather.current.condition.text
            ivWeatherIcon.load(weather.current.condition.icon_url)
        }
    }

    private fun setToday(weather: Weather) {
        binding.todayOverview.apply {
            with(weather.forecast[0]) {
                tvMaxTempValue.text = this.maxTemp.toCelsiusString()
                tvMinTempValue.text = this.minTemp.toCelsiusString()
                tvSunriseValue.text = this.sunrise
                tvSunsetValue.text = this.sunset
                hourAdapter.submitList(this.hourly)
            }
            tvWindValue.text = weather.current.wind.toWindStringMps()
            tvHumidityValue.text = weather.current.humidity.toHumidityString()
        }
    }

    private fun setTomorrow(weather: Weather) {
        binding.tomorrow.apply {
            with(weather.forecast[1]) {
                tvDate.text = this.date.formatDayWeek()
                tvTemp.text = this.temp.toCelsiusString()
                tvWeatherType.text = this.condition.text
                ivWeatherIcon.load(this.condition.icon_url)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}