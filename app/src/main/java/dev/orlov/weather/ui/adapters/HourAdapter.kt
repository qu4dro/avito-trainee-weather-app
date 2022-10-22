package dev.orlov.weather.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import dev.orlov.weather.databinding.ItemHourBinding
import dev.orlov.weather.domain.model.Hour
import dev.orlov.weather.utils.formatTime
import dev.orlov.weather.utils.toCelsiusString

class HourAdapter() : ListAdapter<Hour, HourAdapter.HourViewHolder>(HourDiffUtil) {

    inner class HourViewHolder(
        private val binding: ItemHourBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hour: Hour) {
            binding.apply {
                tvTemp.text = hour.temp.toCelsiusString()
                tvTime.text = hour.time.formatTime()
                ivWeatherIcon.load(hour.condition.icon_url)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
        return HourViewHolder(
            ItemHourBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        val hour = getItem(position)
        holder.bind(hour)

    }

    private object HourDiffUtil : DiffUtil.ItemCallback<Hour>() {
        override fun areItemsTheSame(oldItem: Hour, newItem: Hour): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: Hour, newItem: Hour): Boolean {
            return oldItem == newItem
        }

    }


}