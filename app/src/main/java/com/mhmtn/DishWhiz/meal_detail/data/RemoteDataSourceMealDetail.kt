package com.mhmtn.DishWhiz.meal_detail.data

import com.mhmtn.DishWhiz.core.data.API
import com.mhmtn.DishWhiz.core.domain.util.Resource
import com.mhmtn.DishWhiz.meal_detail.domain.MealDetailDataSource
import com.mhmtn.DishWhiz.meal_detail.domain.MealDetailList

class RemoteDataSourceMealDetail(
    private val api: API
) : MealDetailDataSource {

    override suspend fun getMealDetail(mealId: String): Resource<MealDetailList> {
        val response = try {
            api.getMealDetail(mealId)
        } catch (e: Exception) {
            return Resource.Error(e.toString())
        }
        return Resource.Success(response)
    }
}