package com.mhmtn.DishWhiz.meal.ingredient_meals.presentation

data class IngredientMealsState(
    val isLoading: Boolean = false,
    val meals: List<com.mhmtn.DishWhiz.meal.meal.domain.Meal> = emptyList(),
    val error: String? = null
)
