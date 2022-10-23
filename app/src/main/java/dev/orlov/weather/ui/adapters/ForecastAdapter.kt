package dev.orlov.weather.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import dev.orlov.weather.databinding.ItemForecastBinding
import dev.orlov.weather.domain.model.Forecast
import dev.orlov.weather.utils.formatDayWeek
import dev.orlov.weather.utils.toCelsiusString

class ForecastAdapter() :
    ListAdapter<Forecast, ForecastAdapter.ForecastViewHolder>(ForecastDiffUtil) {

    inner class ForecastViewHolder(
        private val binding: ItemForecastBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(forecast: Forecast) {
            binding.apply {
                tvDate.text = forecast.date.formatDayWeek()
                tvTemp.text = forecast.temp.toCelsiusString()
                tvWeatherType.text = forecast.condition.text
                ivWeatherIcon.load(forecast.condition.icon_url)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        return ForecastViewHolder(
            ItemForecastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecast = getItem(position)
        holder.bind(forecast)

    }

    private object ForecastDiffUtil : DiffUtil.ItemCallback<Forecast>() {
        override fun areItemsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
            return oldItem == newItem
        }

    }


}