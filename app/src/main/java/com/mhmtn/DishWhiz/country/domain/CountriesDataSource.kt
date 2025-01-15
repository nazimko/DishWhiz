package com.mhmtn.DishWhiz.country.domain

import com.mhmtn.DishWhiz.core.domain.util.Resource

interface CountriesDataSource {

    suspend fun getCountries(): Resource<Countries>

}