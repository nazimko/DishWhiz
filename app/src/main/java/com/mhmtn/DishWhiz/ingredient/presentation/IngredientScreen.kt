package com.mhmtn.DishWhiz.ingredient.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhmtn.DishWhiz.core.domain.navigation.Destination
import com.mhmtn.DishWhiz.core.domain.util.Constants.IMAGE_URL
import com.mhmtn.DishWhiz.core.presentation.CustomCard
import com.mhmtn.DishWhiz.core.presentation.SearchBar
import com.mhmtn.DishWhiz.ingredient.domain.Meal
import com.mhmtn.DishWhiz.ui.theme.Acik
import com.mhmtn.DishWhiz.ui.theme.RecipeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientScreen(
    state: IngredientState,
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
    onNavigate: (String) -> Unit
) {
    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Box(
            modifier = modifier.background(color = Acik)
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(color = Acik)
            ) {
                SearchBar(
                    modifier = Modifier
                        .background(color = Acik)
                        .padding(16.dp),
                    onSearch = {
                        onSearch(it)
                    }
                )
                LazyColumn(
                    modifier = modifier
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    items(state.ingredients) {
                        IngredientRow(
                            meal = it,
                            onClick = onNavigate
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun IngredientRow(meal: Meal, modifier: Modifier = Modifier, onClick: (String) -> Unit) {
    Column(
        modifier = modifier.clickable {
            onClick(
                Destination.IngredientDetailScreen.createRoute(
                    mealName = meal.strIngredient
                )
            )
        }
    ) {
        CustomCard(
            title = meal.strIngredient,
            model = IMAGE_URL + "${meal.strIngredient}.png",
            description = meal.strDescription ?: ""
        )
    }
}
@Preview(showBackground = true)
@Composable
fun IngredientScreenPreview() {
    RecipeTheme {
        IngredientScreen(
            state = IngredientState(
                ingredients = (1..15).map {
                    Meal(
                        idIngredient = "$it",
                        strDescription = "Description $it",
                        strIngredient = "Ingredient $it",
                        strType = null
                    )
                }
            ),
            onSearch = {},
            onNavigate = {}
        )
    }
}