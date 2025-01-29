package com.mhmtn.DishWhiz.meal_detail.presentation

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.mhmtn.DishWhiz.meal_detail.domain.MealDetail
import com.mhmtn.DishWhiz.ui.theme.RecipeTheme

@Composable
fun DetailScreen(
    state: MealDetailState,
    modifier: Modifier = Modifier,
    onWatchYoutube: (String) -> Unit
) {

    val imageMinHeight = 72.dp
    val imageMaxHeight = 250.dp
    var imageHeight by remember { mutableStateOf(250.dp) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                imageHeight = (imageHeight + delta.dp).coerceIn(imageMinHeight, imageMaxHeight)
                return Offset.Zero
            }
        }
    }

    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .nestedScroll(nestedScrollConnection)
        ) {
            state.mealDetail?.let {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(imageHeight)
                        .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                        .shadow(4.dp)
                ) {
                    SubcomposeAsyncImage(
                        model = state.mealDetail.strMealThumb,
                        contentDescription = state.mealDetail.strMeal,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                            .shadow(4.dp),
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
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Yemek Bilgileri
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = state.mealDetail.strMeal,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${state.mealDetail.strCategory} - ${state.mealDetail.strArea}",
                        style = MaterialTheme.typography.displaySmall,
                        fontStyle = FontStyle.Italic,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                val tabTitles = listOf("Ingredients", "Instructions")
                val selectedTabIndex = remember { mutableIntStateOf(0) }

                Column (
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    TabRow(
                        selectedTabIndex = selectedTabIndex.intValue,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        tabTitles.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTabIndex.intValue == index,
                                onClick = { selectedTabIndex.intValue = index },
                                text = { Text(text = title) }
                            )
                        }
                    }

                    when (selectedTabIndex.intValue) {
                        0 -> {

                            LazyColumn(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                val ingredientsList = state.mealDetail.getIngredientMeasurePairs()
                                items(ingredientsList.size) { ingredient ->
                                    Text(
                                        text = "${ingredientsList[ingredient].second} ${ingredientsList[ingredient].first}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }

                        1 -> {

                            Text(
                                text = state.mealDetail.strInstructions,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .verticalScroll(rememberScrollState())
                            )
                        }
                    }

                    Button(
                        onClick = {onWatchYoutube(state.mealDetail.strYoutube)},
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(48.dp),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text(
                            text = "Watch on YouTube",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
fun MealDetail.getIngredientMeasurePairs(): List<Pair<String, String>> {
    return (1..20).mapNotNull { index ->
        val ingredient = this::class.members.find { it.name == "strIngredient$index" }?.call(this) as? String
        val measure = this::class.members.find { it.name == "strMeasure$index" }?.call(this) as? String

        if (!ingredient.isNullOrBlank() && !measure.isNullOrBlank()) {
            Pair(ingredient, measure)
        } else null
    }
}



@Preview(showBackground = true)
@Composable
fun EkranPreview() {
    RecipeTheme {
        DetailScreen(
            state = MealDetailState(
                mealDetail = MealDetail(
                    idMeal = "53006",
                    strMeal = "Moussaka",
                    strDrinkAlternate = null,
                    strCategory = "Beef",
                    strArea = "Greek",
                    strInstructions = "Heat the grill to high. Brown the beef in a deep ovenproof frying pan over a high heat for 5 mins. Meanwhile, prick the aubergine with a fork, then microwave on High for 3-5 mins until soft. Mix the yogurt, egg and parmesan together, then add a little seasoning.\r\n\r\nStir the tomatoes, purée and potatoes in with the beef with some seasoning and heat through. Smooth the surface of the beef mixture with the back of a spoon, then slice the cooked aubergine and arrange on top. Pour the yogurt mixture over the aubergines, smooth out evenly, then grill until the topping has set and turned golden.",
                    strMealThumb = "https://www.themealdb.com/images/media/meals/ctg8jd1585563097.jpg",
                    strTags = null,
                    strYoutube = "https://www.youtube.com/watch?v=8U_29i9Qp5U",
                    strIngredient1 = "Beef",
                    strIngredient2 = "Aubergine",
                    strIngredient3 = "Greek Yogurt",
                    strIngredient4 = "Egg",
                    strIngredient5 = "Parmesan",
                    strIngredient6 = "Tomato",
                    strIngredient7 = "Tomato Puree",
                    strIngredient8 = "Potatoes",
                    strIngredient9 = "",
                    strIngredient10 = "",
                    strIngredient11 = "",
                    strIngredient12 = "",
                    strIngredient13 = "",
                    strIngredient14 = "",
                    strIngredient15 = "",
                    strIngredient16 = "",
                    strIngredient17 = "",
                    strIngredient18 = "",
                    strIngredient19 = "",
                    strIngredient20 = "",
                    strMeasure1 = "500g",
                    strMeasure2 = "1 large",
                    strMeasure3 = "150g",
                    strMeasure4 = "1 beaten",
                    strMeasure5 = "3 tbs",
                    strMeasure6 = "400g",
                    strMeasure7 = "4 tbs",
                    strMeasure8 = "350g",
                    strMeasure9 = " ",
                    strMeasure10 = " ",
                    strMeasure11 = " ",
                    strMeasure12 = " ",
                    strMeasure13 = " ",
                    strMeasure14 = " ",
                    strMeasure15 = " ",
                    strMeasure16 = " ",
                    strMeasure17 = " ",
                    strMeasure18 = " ",
                    strMeasure19 = " ",
                    strMeasure20 = " ",
                    strSource = "https://www.bbcgoodfood.com/recipes/must-make-moussaka",
                    strImageSource = null,
                    strCreativeCommonsConfirmed = null,
                    dateModified = null
                )
            ),
            modifier = Modifier,
            onWatchYoutube = {}
        )
    }
}