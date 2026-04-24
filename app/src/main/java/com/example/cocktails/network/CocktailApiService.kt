package com.example.cocktails.network

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL = "https://thecocktaildb.com/api/json/v1/1/"

fun getRetrofit(context: Context) : Retrofit {
    val cache = Cache(context.cacheDir, 10 * 1024 * 1024)
    val okHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor { chain ->
            var request = chain.request()
            if (!isNetworkAvailable(context)) {
                request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-state=604800")
                    .build()
            }
            chain.proceed(request)
        }
        .addNetworkInterceptor { chain ->
            val response = chain.proceed(chain.request())
            response.newBuilder()
                .header("Cache-control", "public, max-age=600")
                .build()
        }
        .build()

    return Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()
}

interface CocktailApiService{
    @GET("filter.php?a=Alcoholic")
    suspend fun getAlcoholic(): AlcoholicCocktailList
    @GET("filter.php?a=Non_Alcoholic")
    suspend fun getNonAlcoholic(): NonAlcoholicCocktailList
}

private var retrofitService: CocktailApiService ?= null

object CocktailApi {
    fun getRetrofitService(context: Context): CocktailApiService {
        if (retrofitService == null){
            retrofitService = getRetrofit(context = context).create(CocktailApiService::class.java)
        }
        return retrofitService!!
    }
}