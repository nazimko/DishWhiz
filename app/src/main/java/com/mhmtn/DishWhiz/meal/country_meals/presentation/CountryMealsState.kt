package com.mhmtn.DishWhiz.meal.country_meals.presentation

import com.mhmtn.DishWhiz.meal.meal.domain.Meal

data class CountryMealsState(
    val meals: List<Meal> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
