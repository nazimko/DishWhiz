package com.mhmtn.DishWhiz.meal.country_meals.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmtn.DishWhiz.core.domain.util.Constants.COUNTRY_NAME
import com.mhmtn.DishWhiz.core.domain.util.Resource
import com.mhmtn.DishWhiz.meal.country_meals.data.RemoteDataSourceCountryMeals
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CountryMealsViewModel(
    private val dataSource: RemoteDataSourceCountryMeals,
    stateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(CountryMealsState())
    val state = _state.onStart {
        stateHandle.get<String>(COUNTRY_NAME)?.let {
            getCountryMeals(it)
        }
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            CountryMealsState()
        )
    private fun getCountryMeals(countryName: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            val result = dataSource.getMeals(countryName)

            when (result) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            meals = result.data!!.meals,
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