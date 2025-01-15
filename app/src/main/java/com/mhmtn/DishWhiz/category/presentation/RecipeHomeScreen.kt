package com.mhmtn.DishWhiz.category.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import com.mhmtn.DishWhiz.R
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.mhmtn.DishWhiz.category.domain.Category
import com.mhmtn.DishWhiz.core.domain.navigation.Destination
import com.mhmtn.DishWhiz.ui.theme.Acik
import com.mhmtn.DishWhiz.ui.theme.RecipeTheme

@Composable
fun RecipeHomeScreen(
    state: CategoriesState,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    if (state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = Acik)
        ) {
            LazyColumn(
                modifier = modifier.padding(4.dp)
            ) {
                items(state.categories) { category ->
                    CategoryCard(
                        category = category,
                        onRecipeClick = onNavigate
                    )
                }
            }
        }
    }
}
@Composable
fun CategoryCard(category: Category, onRecipeClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onRecipeClick(
                    Destination.MealScreen.createRoute(
                        category.strCategory
                    )
                )
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Acik)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubcomposeAsyncImage(
                model = category.strCategoryThumb,
                contentDescription = category.strCategory,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .size(120.dp)
                    .clip(RoundedCornerShape(16.dp)),
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
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    category.strCategory,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                   // fontFamily = FontFamily(Font(R.font.baloo_da))
                )
                Text(
                    category.strCategoryDescription,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3
                )
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(thickness = 2.dp)
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun RecipeHomeScreenPreview() {
    RecipeTheme {
        RecipeHomeScreen(
            state = CategoriesState(
                categories = (1..15).map {
                    Category(
                        idCategory = it.toString(),
                        strCategory = "Category $it",
                        strCategoryDescription = "Category description $it",
                        strCategoryThumb = ""
                    )
                }
            ),
            onNavigate = {}
        )
    }
}