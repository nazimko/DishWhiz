package com.mhmtn.DishWhiz

import SetSystemBarColor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.ads.MobileAds
import com.mhmtn.DishWhiz.category.presentation.CategoryViewModel
import com.mhmtn.DishWhiz.category.presentation.RecipeHomeScreen
import com.mhmtn.DishWhiz.core.domain.ad.ScreenVisitManager
import com.mhmtn.DishWhiz.core.domain.navigation.Destination
import com.mhmtn.DishWhiz.core.presentation.BottomBar
import com.mhmtn.DishWhiz.core.presentation.TopBar
import com.mhmtn.DishWhiz.country.presentation.CountriesScreen
import com.mhmtn.DishWhiz.country.presentation.CountriesViewModel
import com.mhmtn.DishWhiz.ingredient.presentation.IngredientScreen
import com.mhmtn.DishWhiz.ingredient.presentation.IngredientViewModel
import com.mhmtn.DishWhiz.meal.country_meals.presentation.CountryMealsViewModel
import com.mhmtn.DishWhiz.meal.ingredient_meals.presentation.IngredientMealsViewModel
import com.mhmtn.DishWhiz.meal.meal.presentation.MealListScreen
import com.mhmtn.DishWhiz.meal.meal.presentation.MealViewModel
import com.mhmtn.DishWhiz.meal_detail.presentation.DetailScreen
import com.mhmtn.DishWhiz.meal_detail.presentation.MealDetailViewModel
import com.mhmtn.DishWhiz.ui.theme.RecipeTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    private lateinit var screenVisitManager: ScreenVisitManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        MobileAds.initialize(this)
        setContent {
            screenVisitManager = ScreenVisitManager(this)
            SetSystemBarColor()
            RecipeTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomBar(navController = navController) },
                    topBar = { TopBar() }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Destination.HomeScreen.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Destination.HomeScreen.route) {
                            val viewModel = koinViewModel<CategoryViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()
                            RecipeHomeScreen(
                                state = state,
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Destination.MealScreen.route) {
                            val viewModel = koinViewModel<MealViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()
                            MealListScreen(
                                state = state,
                                onNavigate = navController::navigate
                            )
                        }

                        composable(Destination.MealDetailScreen.route) {
                            val activity = LocalContext.current as ComponentActivity
                            val uriHandler = LocalUriHandler.current
                            val viewModel = koinViewModel<MealDetailViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()
                            DetailScreen(
                                state = state,
                                activity = activity,
                                onWatchYoutube = {
                                    viewModel.onYoutubeClick(uriHandler, it)
                                }
                            )
                        }
                        composable(Destination.CountriesScreen.route) {
                            val viewModel = koinViewModel<CountriesViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()
                            CountriesScreen(
                                state = state,
                                onNavigate = navController::navigate
                            )
                        }

                        composable(Destination.CountryDetailScreen.route) {
                            val viewModel = koinViewModel<CountryMealsViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()
                            MealListScreen(
                                state2 = state,
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Destination.IngredientScreen.route) {
                            val viewModel = koinViewModel<IngredientViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()
                            IngredientScreen(
                                state = state,
                                onNavigate = navController::navigate,
                                onSearch = viewModel::onSearch
                            )
                        }
                        composable(Destination.IngredientDetailScreen.route) {
                            val viewModel = koinViewModel<IngredientMealsViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()
                            MealListScreen(
                                state3 = state,
                                onNavigate = navController::navigate
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        screenVisitManager.resetVisitCount()
    }
}