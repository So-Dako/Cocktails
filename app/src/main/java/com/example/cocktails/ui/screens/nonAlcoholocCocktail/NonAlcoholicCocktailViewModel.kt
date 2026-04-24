package com.example.cocktails.ui.screens.nonAlcoholocCocktail

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktails.network.CocktailApiService
import com.example.cocktails.network.NonAlcoholicCocktail
import com.example.cocktails.network.getRetrofit
import com.example.cocktails.network.isNetworkAvailable
import kotlinx.coroutines.launch
import okhttp3.Cache
import okhttp3.OkHttpClient


sealed interface NonAlcoholicCocktailUiState{
    data class Success(val nonAlcoholicCocktailList: List<NonAlcoholicCocktail>): NonAlcoholicCocktailUiState
    object Loading: NonAlcoholicCocktailUiState
    object Error: NonAlcoholicCocktailUiState
}

class NonAlcoholicCocktailViewModel(application: Application) : AndroidViewModel(application){

    var nonAlcoholicCocktailUiState: NonAlcoholicCocktailUiState by mutableStateOf(
        NonAlcoholicCocktailUiState.Loading)
        private set

    private val context = getApplication<Application>()

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

    private val retrofitService : CocktailApiService by lazy {
        getRetrofit(okHttpClient).create(CocktailApiService::class.java)
    }

    init{
        getNonAlcoholicCocktails()
    }

    fun getNonAlcoholicCocktails() {
        viewModelScope.launch {
            nonAlcoholicCocktailUiState = try {
                val nonAlcoholic = retrofitService.getNonAlcoholic()
                val listResult = nonAlcoholic.drinks
                NonAlcoholicCocktailUiState.Success(listResult)
            } catch (e: Exception) {
                NonAlcoholicCocktailUiState.Error
            }
        }
    }
}