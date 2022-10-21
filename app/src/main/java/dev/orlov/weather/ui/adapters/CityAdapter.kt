package dev.orlov.weather.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.orlov.weather.databinding.ItemCityBinding
import dev.orlov.weather.domain.model.City

class CityAdapter(
    private val clickListener: OnItemClickListener,
) :
    ListAdapter<City, CityAdapter.CityViewHolder>(CityDiffUtil) {

    interface OnItemClickListener {
        fun onCityClick(city: City)
    }

    inner class CityViewHolder(
        private val binding: ItemCityBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(city: City) {
            binding.apply {
                tvName.text = city.name
                tvCountry.text = city.country
                root.setOnClickListener { clickListener.onCityClick(city) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(
            ItemCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = getItem(position)
        holder.bind(city)

    }

    private object CityDiffUtil : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }

    }


}