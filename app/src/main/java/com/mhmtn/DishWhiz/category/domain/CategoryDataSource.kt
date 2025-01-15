package com.mhmtn.DishWhiz.category.domain

import com.mhmtn.DishWhiz.core.domain.util.Resource

interface CategoryDataSource {

    suspend fun getCategories(): Resource<CategoriesModel>

}