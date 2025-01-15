package com.mhmtn.DishWhiz.ingredient.data

import com.mhmtn.DishWhiz.core.data.API
import com.mhmtn.DishWhiz.core.domain.util.Resource
import com.mhmtn.DishWhiz.ingredient.domain.Ingredient
import com.mhmtn.DishWhiz.ingredient.domain.IngredientDataSource

class RemoteDataSourceIngredient(
    private val api: API
) : IngredientDataSource {

    override suspend fun getIngredients(): Resource<Ingredient> {
        val response = try {
            api.getIngredients()
        } catch (e: Exception) {
            return Resource.Error(e.toString())
        }
        return Resource.Success(response)
    }
}