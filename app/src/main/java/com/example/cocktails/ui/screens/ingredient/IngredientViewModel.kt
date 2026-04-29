package com.example.cocktails.ui.screens.ingredient

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktails.network.Cocktail
import com.example.cocktails.network.CocktailApiService
import com.example.cocktails.network.OkHttpClientManager
import com.example.cocktails.network.getRetrofit
import kotlinx.coroutines.launch

sealed interface IngredientUiState{
    data class Success(val cocktailList: List<Cocktail>): IngredientUiState
    object Loading: IngredientUiState
    object Error: IngredientUiState
}

class IngredientViewModel(application: Application) : AndroidViewModel(application){

    var ingredientUiState: IngredientUiState by mutableStateOf(
        IngredientUiState.Loading)
        private set

    private val context = getApplication<Application>()

    val retrofitService: CocktailApiService by lazy {
        getRetrofit(OkHttpClientManager.getOkHttpClient(context))
            .create(CocktailApiService::class.java)
    }

    fun getCocktails(ingredient: String) {
        viewModelScope.launch {
            ingredientUiState = try {
                val cocktails = retrofitService.getDrinksByIngredient(ingredient)
                val listResult = cocktails.drinks
                    IngredientUiState.Success(listResult)
            } catch (e: Exception) {
                IngredientUiState.Error
            }
        }
    }
}