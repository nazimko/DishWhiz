package com.mhmtn.DishWhiz.country.data

import retrofit2.http.GET
import retrofit2.http.Path

interface FlagApiService {
    @GET("flags/{countryCode}.png")
    suspend fun getFlag(@Path("countryCode") countryCode: String): String
} 