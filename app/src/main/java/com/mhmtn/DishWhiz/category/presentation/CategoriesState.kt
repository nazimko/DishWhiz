package com.mhmtn.DishWhiz.category.presentation

import com.mhmtn.DishWhiz.category.domain.Category

data class CategoriesState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val error: String? = null,
)

