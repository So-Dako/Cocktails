package com.example.cocktails.ui.screens.nonAlcoholocCocktail

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktails.network.CocktailApi
import com.example.cocktails.network.NonAlcoholicCocktail
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

    init{
        getNonAlcoholicCocktails()
    }

    fun getNonAlcoholicCocktails() {
        viewModelScope.launch {
            nonAlcoholicCocktailUiState = try {
                val nonAlcoholic = CocktailApi.getRetrofitService(getApplication()).getNonAlcoholic()
                val listResult = nonAlcoholic.drinks
                NonAlcoholicCocktailUiState.Success(listResult)
            } catch (e: Exception) {
                NonAlcoholicCocktailUiState.Error
            }
        }
    }
}