package com.mhmtn.DishWhiz.ingredient.domain

import com.mhmtn.DishWhiz.core.domain.util.Resource

interface IngredientDataSource {

    suspend fun getIngredients(): Resource<Ingredient>

}