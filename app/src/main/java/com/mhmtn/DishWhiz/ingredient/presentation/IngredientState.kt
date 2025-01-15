package com.mhmtn.DishWhiz.ingredient.presentation

import com.mhmtn.DishWhiz.ingredient.domain.Meal

data class IngredientState (
    val ingredients: List<Meal> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)