package com.mhmtn.DishWhiz.core.data

import com.mhmtn.DishWhiz.category.domain.CategoriesModel
import com.mhmtn.DishWhiz.country.domain.Countries
import com.mhmtn.DishWhiz.ingredient.domain.Ingredient
import com.mhmtn.DishWhiz.meal.meal.domain.Meals
import com.mhmtn.DishWhiz.meal_detail.domain.MealDetailList
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("categories.php")
    suspend fun getCategories(): CategoriesModel

    @GET("filter.php")
    suspend fun getMeals(
        @Query("c") categoryName: String
    ): Meals

    @GET("lookup.php")
    suspend fun getMealDetail(
        @Query("i") mealId: String
    ): MealDetailList

    @GET("list.php")
    suspend fun getCountries(
        @Query("a") categoryName: String = "list"
    ): Countries

    @GET("filter.php")
    suspend fun getCountryMeals(
        @Query("a") countryName: String
    ): Meals

    @GET("list.php")
    suspend fun getIngredients(
        @Query("i") ingredientName: String = "list"
    ): Ingredient

    @GET("filter.php")
    suspend fun getIngredientMeals(
        @Query("i") ingredientName: String
    ):Meals


}