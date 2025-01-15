package com.mhmtn.DishWhiz.category.data

import com.mhmtn.DishWhiz.category.domain.CategoryDataSource
import com.mhmtn.DishWhiz.category.domain.CategoriesModel
import com.mhmtn.DishWhiz.core.data.API
import com.mhmtn.DishWhiz.core.domain.util.Resource

class RemoteDataSourceCategory(
    private val api: API
) : CategoryDataSource {

    override suspend fun getCategories(): Resource<CategoriesModel> {
        val response = try {
            api.getCategories()
        } catch (e: Exception){
            return Resource.Error(e.toString())
        }
        return Resource.Success(response)
    }
}