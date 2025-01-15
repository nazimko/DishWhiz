package com.mhmtn.DishWhiz.di

import com.mhmtn.DishWhiz.category.data.RemoteDataSourceCategory
import com.mhmtn.DishWhiz.category.domain.CategoryDataSource
import com.mhmtn.DishWhiz.category.presentation.CategoryViewModel
import com.mhmtn.DishWhiz.core.data.API
import com.mhmtn.DishWhiz.core.domain.util.Constants.BASE_URL
import com.mhmtn.DishWhiz.country.data.RemoteDataSourceCountries
import com.mhmtn.DishWhiz.country.domain.CountriesDataSource
import com.mhmtn.DishWhiz.country.presentation.CountriesViewModel
import com.mhmtn.DishWhiz.ingredient.data.RemoteDataSourceIngredient
import com.mhmtn.DishWhiz.ingredient.domain.IngredientDataSource
import com.mhmtn.DishWhiz.ingredient.presentation.IngredientViewModel
import com.mhmtn.DishWhiz.meal.country_meals.data.RemoteDataSourceCountryMeals
import com.mhmtn.DishWhiz.meal.country_meals.presentation.CountryMealsViewModel
import com.mhmtn.DishWhiz.meal.ingredient_meals.data.RemoteDataSourceIngredientMeals
import com.mhmtn.DishWhiz.meal.ingredient_meals.presentation.IngredientMealsViewModel
import com.mhmtn.DishWhiz.meal.meal.data.RemoteDataSourceMeal
import com.mhmtn.DishWhiz.meal.meal.domain.MealDataSource
import com.mhmtn.DishWhiz.meal.meal.presentation.MealViewModel
import com.mhmtn.DishWhiz.meal_detail.data.RemoteDataSourceMealDetail
import com.mhmtn.DishWhiz.meal_detail.domain.MealDetailDataSource
import com.mhmtn.DishWhiz.meal_detail.presentation.MealDetailViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.core.qualifier.qualifier
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(API::class.java)
    }

    singleOf(::RemoteDataSourceCategory).bind<CategoryDataSource>()
    viewModelOf(::CategoryViewModel)

    single(named("mealDataSource")) { RemoteDataSourceMeal(api = get()) }.bind<MealDataSource>()
    viewModel {
        MealViewModel(
            dataSource = get(qualifier(named("mealDataSource").toString())),
            stateHandle = get()
        )
    }

    singleOf(::RemoteDataSourceMealDetail).bind<MealDetailDataSource>()
    viewModelOf(::MealDetailViewModel)

    singleOf(::RemoteDataSourceCountries).bind<CountriesDataSource>()
    viewModelOf(::CountriesViewModel)

    single(named("countryMealDataSource")) { RemoteDataSourceCountryMeals(api = get()) }.bind<MealDataSource>()
    viewModel {
        CountryMealsViewModel(
            dataSource = get(qualifier(named("countryMealDataSource").toString())),
            stateHandle = get()
        )
    }
    singleOf(::RemoteDataSourceIngredient).bind<IngredientDataSource>()
    viewModelOf(::IngredientViewModel)
    single(named("ingredientMealDataSource")) { RemoteDataSourceIngredientMeals(api = get()) }.bind<MealDataSource>()
    viewModel {
        IngredientMealsViewModel(
            dataSource = get(qualifier(named("ingredientMealDataSource").toString())),
            savedStateHandle = get()
        )
    }
}