package com.mhmtn.DishWhiz.country.data

import com.mhmtn.DishWhiz.core.data.API
import com.mhmtn.DishWhiz.core.domain.util.Resource
import com.mhmtn.DishWhiz.country.domain.Countries
import com.mhmtn.DishWhiz.country.domain.CountriesDataSource

class RemoteDataSourceCountries(
    private val api: API
) : CountriesDataSource {

    override suspend fun getCountries(): Resource<Countries> {
        val response = try {
            api.getCountries()
        } catch (e: Exception){
            return Resource.Error(e.toString())
        }
        return Resource.Success(response)
    }
}