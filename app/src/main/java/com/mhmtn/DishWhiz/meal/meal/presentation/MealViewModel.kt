package com.mhmtn.DishWhiz.meal.meal.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmtn.DishWhiz.core.domain.util.Constants.CATEGORY_NAME
import com.mhmtn.DishWhiz.core.domain.util.Resource
import com.mhmtn.DishWhiz.meal.meal.domain.MealDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MealViewModel(
    private val dataSource: MealDataSource,
    private val stateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(MealsState())
    val state = _state
        .onStart {
            stateHandle.get<String>(CATEGORY_NAME)?.let {
                getMeals(it)
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            MealsState()
        )

    private fun getMeals(category: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            val result = dataSource.getMeals(category)
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