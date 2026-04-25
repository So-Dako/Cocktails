package com.example.cocktails.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://thecocktaildb.com/api/json/v1/1/"

fun getRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .client(client)
        .build()
}

interface CocktailApiService{
    @GET("filter.php?a=Alcoholic")
    suspend fun getAlcoholic(): AlcoholicCocktailList
    @GET("filter.php?a=Non_Alcoholic")
    suspend fun getNonAlcoholic(): NonAlcoholicCocktailList
    @GET("lookup.php")
    suspend fun getDrinkDetail(@Query("i") idDrink: String): Drinks
}

object CocktailApi {
    /*val retrofitService: CocktailApiService by lazy {
        retrofit.create(CocktailApiService::class.java)
    }
     */
}