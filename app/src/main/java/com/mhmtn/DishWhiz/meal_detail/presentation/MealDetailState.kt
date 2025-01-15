package com.mhmtn.DishWhiz.meal_detail.presentation

import com.mhmtn.DishWhiz.meal_detail.domain.MealDetail

data class MealDetailState (
    val isLoading: Boolean = false,
    val mealDetail: MealDetail? = null,
    val error: String? = null
)