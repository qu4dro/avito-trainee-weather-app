package dev.orlov.weather.ui.fragments.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialSharedAxis
import dev.orlov.weather.R
import dev.orlov.weather.databinding.FragmentSearchBinding
import dev.orlov.weather.domain.model.City
import dev.orlov.weather.ui.adapters.CityAdapter
import dev.orlov.weather.utils.LoadState
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    val binding
        get() = _binding!!

    private val viewModel: SearchViewModel by activityViewModels()
    private val adapter: CityAdapter = CityAdapter(object : CityAdapter.OnItemClickListener {
        override fun onCityClick(city: City) {
            viewModel.insertCity(city.copy(isMain = true))
            findNavController().navigateUp()
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clearSearchedCities()
        setupUi()
        showKeyBoard()
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    uiState.loadState?.let {
                        when (uiState.loadState) {
                            LoadState.LOADING -> {
                                setLoadingUi()
                            }
                            LoadState.ERROR -> {
                                setErrorUi()
                            }
                            LoadState.SUCCESS -> {
                                setSuccessUi(uiState.cities)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    }

    private fun setLoadingUi() {
        binding.apply {
            rvCities.visibility = View.GONE
        }
    }

    private fun setErrorUi() {
        binding.apply {
            rvCities.visibility = View.GONE
        }
    }

    private fun setSuccessUi(cities: List<City>) {
        binding.apply {
            adapter.submitList(cities)
            rvCities.visibility = View.VISIBLE
        }
    }

    private fun setupUi() {
        binding.apply {
            rvCities.adapter = adapter
            btnBack.setOnClickListener { findNavController().navigateUp() }
            edtSearch.doOnTextChanged { text, _, _, _ ->
                text?.let {
                    if (it.length > 1) viewModel.searchCity(text.toString().trim())
                    else {
                        viewModel.clearSearchedCities()
                    }
                }
            }
        }
    }

    private fun showKeyBoard() {
        binding.edtSearch.requestFocus()
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.showSoftInput( binding.edtSearch, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}