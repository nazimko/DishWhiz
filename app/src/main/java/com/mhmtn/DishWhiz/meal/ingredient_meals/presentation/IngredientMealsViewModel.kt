package com.mhmtn.DishWhiz.meal.ingredient_meals.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmtn.DishWhiz.core.domain.util.Constants.MEAL_NAME
import com.mhmtn.DishWhiz.core.domain.util.Resource
import com.mhmtn.DishWhiz.meal.ingredient_meals.data.RemoteDataSourceIngredientMeals
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IngredientMealsViewModel(
    savedStateHandle: SavedStateHandle,
    private val dataSource: RemoteDataSourceIngredientMeals
) : ViewModel() {

    private val _state = MutableStateFlow(IngredientMealsState())
    val state = _state.asStateFlow()

    init {
        savedStateHandle.get<String>(MEAL_NAME)?.let {
            getIngredientMeals(it)
        }
    }
    private fun getIngredientMeals(ingredientName: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            val result = dataSource.getMeals(ingredientName)

            when (result) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            meals = result.data?.meals ?: emptyList(),
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