package com.example.cocktails.ui.screens.alcoholicCocktail

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktails.network.AlcoholicCocktail
import com.example.cocktails.network.CocktailApiService
import com.example.cocktails.network.getRetrofit
import com.example.cocktails.network.isNetworkAvailable
import kotlinx.coroutines.launch
import okhttp3.Cache
import okhttp3.OkHttpClient

sealed interface AlcoholicCocktailUiState {
    data class Success(val alcoholicCocktailList: List<AlcoholicCocktail>): AlcoholicCocktailUiState
    object Loading: AlcoholicCocktailUiState
    object Error: AlcoholicCocktailUiState
}

class AlcoholicCocktailViewModel(application: Application): AndroidViewModel(application){

    var alcoholicCocktailUiState: AlcoholicCocktailUiState by mutableStateOf(AlcoholicCocktailUiState.Loading)
    private set

    val context = getApplication<Application>()

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

    val retrofitService : CocktailApiService by lazy {
        getRetrofit(okHttpClient).create(CocktailApiService::class.java)
    }

    init {
        getAlcoholicCocktails()
    }

    fun getAlcoholicCocktails(){
        viewModelScope.launch {
            alcoholicCocktailUiState = try {
                val alcoholic = retrofitService.getAlcoholic()
                val listResult = alcoholic.drinks
                AlcoholicCocktailUiState.Success(listResult)
            } catch (e: Exception) {
                AlcoholicCocktailUiState.Error
            }
        }
    }
}