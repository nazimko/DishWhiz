package com.mhmtn.DishWhiz.core.domain.navigation

import com.mhmtn.DishWhiz.core.domain.util.Constants.CATEGORY_NAME
import com.mhmtn.DishWhiz.core.domain.util.Constants.COUNTRY_NAME
import com.mhmtn.DishWhiz.core.domain.util.Constants.MEAL_ID
import com.mhmtn.DishWhiz.core.domain.util.Constants.MEAL_NAME

sealed class Destination(val route: String) {

    data object HomeScreen : Destination("HomeScreen")
    data object MealScreen : Destination("MealScreen/{${CATEGORY_NAME}}"){
        fun createRoute(categoryName: String) = "MealScreen/$categoryName"
    }
    data object MealDetailScreen : Destination("MealDetailScreen/{${MEAL_ID}}"){
        fun createRoute(mealId: String) = "MealDetailScreen/$mealId"
    }
    data object CountriesScreen : Destination("CountryScreen")
    data object CountryDetailScreen : Destination("CountryDetailScreen/{${COUNTRY_NAME}}"){
        fun createRoute(countryName: String) = "CountryDetailScreen/$countryName"
    }
    data object IngredientScreen : Destination("IngredientScreen")
    data object IngredientDetailScreen : Destination("IngredientDetailScreen/{${MEAL_NAME}}"){
        fun createRoute(mealName: String) = "IngredientDetailScreen/$mealName"
    }
}