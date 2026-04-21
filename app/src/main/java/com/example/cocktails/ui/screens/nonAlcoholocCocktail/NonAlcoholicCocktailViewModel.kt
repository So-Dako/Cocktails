package com.example.cocktails.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cocktails.network.NonAlcoholicCocktailList
import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import com.example.cocktails.network.CocktailApi
import com.example.cocktails.network.NonAlcoholicCocktail
import kotlinx.coroutines.launch


sealed interface NonAlcoholicCocktailUiState{
    data class Success(val nonAlcoholicCocktailList: List<NonAlcoholicCocktail>): NonAlcoholicCocktailUiState
    object Loading: NonAlcoholicCocktailUiState
    object Error: NonAlcoholicCocktailUiState
}

class NonAlcoholicCocktailViewModel : ViewModel(){

    var nonAlcoholicCocktailUiState: NonAlcoholicCocktailUiState by mutableStateOf(
        NonAlcoholicCocktailUiState.Loading)
        private set

    init {
        getNonAlcoholicCocktails()
    }

    private fun getNonAlcoholicCocktails() {
        viewModelScope.launch {
            nonAlcoholicCocktailUiState = try {
                val nonAlcoholic = CocktailApi.retrofitService.getNonAlcoholic()
                val listResult = nonAlcoholic.drinks
                NonAlcoholicCocktailUiState.Success(listResult)
            } catch (e: Exception) {
                NonAlcoholicCocktailUiState.Error
            }
        }
    }
}