package com.mhmtn.DishWhiz.meal.meal.presentation

import com.mhmtn.DishWhiz.meal.meal.domain.Meal

data class MealsState(
    val isLoading: Boolean = false,
    val meals: List<Meal> = emptyList(),
    val error: String? = null
)
