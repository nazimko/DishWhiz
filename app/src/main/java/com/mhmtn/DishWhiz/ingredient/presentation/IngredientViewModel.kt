package com.mhmtn.DishWhiz.ingredient.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmtn.DishWhiz.core.domain.util.Resource
import com.mhmtn.DishWhiz.ingredient.domain.IngredientDataSource
import com.mhmtn.DishWhiz.ingredient.domain.Meal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IngredientViewModel(
    private val ingredientDataSource: IngredientDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(IngredientState())
    val state = _state.asStateFlow()

    private var initialIngredientsList = listOf<Meal>()
    private var isSearchStarting = true

    init {
        getIngredients()
    }

    private fun getIngredients() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            val result = ingredientDataSource.getIngredients()

            when (result) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            ingredients = result.data!!.meals,
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

    fun onSearch(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val updatedList = when {
                query.isEmpty() -> {
                    isSearchStarting = true
                    initialIngredientsList
                }
                isSearchStarting -> {
                    initialIngredientsList = _state.value.ingredients
                    isSearchStarting = false
                    filterIngredients(_state.value.ingredients, query)
                }
                else -> {
                    filterIngredients(initialIngredientsList, query)
                }
            }

            _state.update {
                it.copy(ingredients = updatedList)
            }
        }
    }

    private fun filterIngredients(list: List<Meal>, query: String): List<Meal> {
        return list.filter {
            it.strIngredient.contains(query.trim(), ignoreCase = true)
        }
    }
}