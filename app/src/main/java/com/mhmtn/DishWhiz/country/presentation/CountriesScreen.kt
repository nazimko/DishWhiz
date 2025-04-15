package com.mhmtn.DishWhiz.country.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhmtn.DishWhiz.R
import com.mhmtn.DishWhiz.core.domain.navigation.Destination
import com.mhmtn.DishWhiz.ui.theme.Acik
import com.mhmtn.DishWhiz.ui.theme.RecipeTheme

@Composable
fun CountriesScreen(
    state: CountriesState,
    modifier: Modifier = Modifier,
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
        Box (
            modifier = modifier
                .background(color = MaterialTheme.colorScheme.primary)
        ){
            LazyColumn(
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.primary)
            ) {
                items(state.countries) {
                    CountryRow(
                        country = it,
                        onCountryClick = onNavigate
                    )
                }
            }
        }
    }
}

@Composable
fun CountryRow(country: CountryWithFlag, onCountryClick: (String) -> Unit) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .clickable{
                onCountryClick(
                    Destination.CountryDetailScreen.createRoute(
                        countryName = country.name
                    )
                )
            },
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = country.name,
            fontSize = 20.sp,
            modifier = Modifier.width(150.dp),
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.width(16.dp))
        Image(
            painter = painterResource(id = country.flagResId),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Preview(showBackground = true)
@Composable
fun CountriesScreenPreview() {
    RecipeTheme {
        CountriesScreen(
            state = CountriesState(
                countries = (1..15).map {
                    CountryWithFlag(
                        name = "Area $it",
                        flagResId = R.drawable.tr
                    )
                }
            ),
            onNavigate = {}
        )
    }
}