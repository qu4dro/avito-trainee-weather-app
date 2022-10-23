package dev.orlov.weather.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.orlov.weather.databinding.BottomSheetCitiesBinding
import dev.orlov.weather.domain.model.City
import dev.orlov.weather.ui.adapters.CityAdapter

class CitiesBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetCitiesBinding? = null
    val binding
        get() = _binding!!

    private val viewModel: CitiesViewModel by activityViewModels()

    private val adapter: CityAdapter = CityAdapter(object : CityAdapter.OnItemClickListener {
        override fun onCityClick(city: City) {
            viewModel.updateSelectedCity(city)
            this@CitiesBottomSheet.dismiss()
        }
    })

    companion object {
        const val BOTTOM_SHEET_CITIES_TAG = "BOTTOM_SHEET_CITIES"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetCitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.cities.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        setupUi()
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

}