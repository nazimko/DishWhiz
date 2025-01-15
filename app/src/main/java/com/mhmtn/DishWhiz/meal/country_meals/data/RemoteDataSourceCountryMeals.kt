package com.mhmtn.DishWhiz.meal.country_meals.data

import com.mhmtn.DishWhiz.core.data.API
import com.mhmtn.DishWhiz.core.domain.util.Resource
import com.mhmtn.DishWhiz.meal.meal.domain.MealDataSource
import com.mhmtn.DishWhiz.meal.meal.domain.Meals

class RemoteDataSourceCountryMeals(
    private val api: API
) : MealDataSource {
    override suspend fun getMeals(name: String): Resource<Meals> {
        val response = try {
            api.getCountryMeals(name)
        } catch (e: Exception) {
            return Resource.Error(e.toString())
        }
        return Resource.Success(response)
    }
}