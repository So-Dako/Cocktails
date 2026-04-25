package com.example.cocktails.ui.screens.nonAlcoholocCocktail

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktails.network.CocktailApiService
import com.example.cocktails.network.NonAlcoholicCocktail
import com.example.cocktails.network.OkHttpClientManager
import com.example.cocktails.network.getRetrofit
import kotlinx.coroutines.launch


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

    val retrofitService: CocktailApiService by lazy {
        getRetrofit(OkHttpClientManager.getOkHttpClient(context))
            .create(CocktailApiService::class.java)
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