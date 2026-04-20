package com.example.cocktails.network

import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType

private const val BASE_URL = "https://thecocktaildb.com/api/json/v1/1/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface CocktailApiService{
    @GET("filter.php?a=Alcoholic")
    suspend fun getAlcoholic(): DrinksList
}

object CocktailApi {
    val retrofitService: CocktailApiService by lazy {
        retrofit.create(CocktailApiService::class.java)
    }
}