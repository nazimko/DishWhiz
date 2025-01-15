package com.mhmtn.DishWhiz.meal.meal.domain

import com.mhmtn.DishWhiz.core.domain.util.Resource

interface MealDataSource {

    suspend fun getMeals(name: String): Resource<Meals>

}