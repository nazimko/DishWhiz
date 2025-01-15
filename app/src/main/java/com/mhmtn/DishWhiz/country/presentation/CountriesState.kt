package com.mhmtn.DishWhiz.country.presentation

data class CountriesState (
    val countries: List<CountryWithFlag> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)