package com.mhmtn.DishWhiz.country.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmtn.DishWhiz.core.domain.util.Resource
import com.mhmtn.DishWhiz.country.domain.CountriesDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.mhmtn.DishWhiz.R

class CountriesViewModel(
    private val dataSource: CountriesDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(CountriesState())
    val state = _state.asStateFlow()

    init {
        getCountries()
    }

    val countryFlags = mapOf(
        "American" to R.drawable.us,
        "British" to R.drawable.gb,
        "Canadian" to R.drawable.ca,
        "Chinese" to R.drawable.cn,
        "Croatian" to R.drawable.hr,
        "Dutch" to R.drawable.nl,
        "Egyptian" to R.drawable.eg,
        "Filipino" to R.drawable.ph,
        "French" to R.drawable.fr,
        "Greek" to R.drawable.gr,
        "Indian" to R.drawable.`in`,
        "Irish" to R.drawable.ie,
        "Italian" to R.drawable.it,
        "Jamaican" to R.drawable.jm,
        "Japanese" to R.drawable.jp,
        "Kenyan" to R.drawable.ke,
        "Malaysian" to R.drawable.my,
        "Mexican" to R.drawable.mx,
        "Moroccan" to R.drawable.ma,
        "Polish" to R.drawable.pl,
        "Portuguese" to R.drawable.pt,
        "Russian" to R.drawable.ru,
        "Spanish" to R.drawable.es,
        "Thai" to R.drawable.th,
        "Tunisian" to R.drawable.tn,
        "Turkish" to R.drawable.tr,
        "Ukrainian" to R.drawable.ua,
        "Uruguayan" to R.drawable.uy,
        "Vietnamese" to R.drawable.vn)

    private fun getCountries() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            val result = dataSource.getCountries()
            when (result) {
                is Resource.Success -> {
                    val updatedCountries = result.data!!.meals.map { country ->
                        CountryWithFlag(
                            name = country.strArea,
                            flagResId = countryFlags[country.strArea] ?: R.drawable.un
                        )
                    }
                    _state.update {
                        it.copy(
                            countries = updatedCountries,
                            isLoading = false
                        )
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            error = result.message,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}