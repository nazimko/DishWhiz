package com.mhmtn.DishWhiz.meal.meal.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.mhmtn.DishWhiz.core.domain.navigation.Destination
import com.mhmtn.DishWhiz.meal.country_meals.presentation.CountryMealsState
import com.mhmtn.DishWhiz.meal.ingredient_meals.presentation.IngredientMealsState
import com.mhmtn.DishWhiz.meal.meal.domain.Meal
import com.mhmtn.DishWhiz.ui.theme.Acik
import com.mhmtn.DishWhiz.ui.theme.RecipeTheme

@Composable
fun MealListScreen(
    state: MealsState? =null,
    state2 : CountryMealsState? = null,
    state3 : IngredientMealsState? = null,
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {

    if (state?.isLoading ?: state2?.isLoading ?: state3!!.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.background(color = Acik)
        ){
            Text(
                text = "Meals",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            LazyVerticalGrid(
                GridCells.Fixed(2),
                modifier = modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                val colors = listOf(Color(0xFF85B3B1), Color(0xFFF4BA97))
                itemsIndexed(state?.meals ?: state2?.meals ?: state3!!.meals) { index, meal ->
                    val row = index / 2
                    val column = index % 2
                    val colorIndex = (row + column) % 2
                    MealListItem(
                        meal = meal,
                        onClick = onNavigate,
                        cardColor = colors[colorIndex]
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MealListItem(meal: Meal, onClick: (String) -> Unit, cardColor : Color) {
    Card(
        modifier = Modifier
            .clickable { onClick(
                Destination.MealDetailScreen.createRoute(
                    meal.idMeal
                )
            ) }
            .padding(8.dp)
            .width(150.dp)
            .height(180.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            SubcomposeAsyncImage(
                model = meal.strMealThumb,
                contentDescription = meal.strMeal,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight(0.80f)
                    .size(150.dp)
                    .padding(top=4.dp)
                    .clip(RoundedCornerShape(16.dp)),
                alignment = Alignment.Center,
                loading = {
                    Box(contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                },
                error = {
                    Icon(
                        imageVector = Icons.Default.Info, contentDescription = null,
                        tint = Color.Red
                    )
                })
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = meal.strMeal,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth().basicMarquee(),
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MealListScreenPreview() {
    RecipeTheme {
        MealListScreen(
            state = MealsState(
                meals = (1..8).map {
                    Meal(
                        idMeal = it.toString(),
                        strMeal = "Meal $it",
                        strMealThumb = ""
                    )
                }
            ),
            onNavigate = {},
            state2 = CountryMealsState(
                meals = (1..8).map {
                    Meal(
                        idMeal = it.toString(),
                        strMeal = "Meal $it",
                        strMealThumb = ""
                    )
                }
            ),
            state3 = null
        )
    }
}