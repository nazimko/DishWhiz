package com.mhmtn.DishWhiz.meal_detail.domain

import com.mhmtn.DishWhiz.core.domain.util.Resource

interface MealDetailDataSource {
    suspend fun getMealDetail(mealId: String): Resource<MealDetailList>
}