package com.example.cocktails.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

private val json = Json { ignoreUnknownKeys = true }

fun getRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .client(client)
        .build()
}

interface CocktailApiService{
    @GET("filter.php")
    suspend fun getAlcoholic(@Query("a") alcoholic: String = "Alcoholic"): CocktailList
    @GET("filter.php")
    suspend fun getNonAlcoholic(@Query("a") alcoholic: String = "Non_Alcoholic"): CocktailList
    @GET("lookup.php")
    suspend fun getDrinkDetail(@Query("i") idDrink: String): Drink
    @GET("filter.php")
    suspend fun getDrinksByIngredient(@Query("i") ingredient: String): CocktailList
}

object CocktailApi {}