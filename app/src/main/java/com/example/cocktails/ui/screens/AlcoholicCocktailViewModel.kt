package com.example.cocktails.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktails.network.CocktailApi
import kotlinx.coroutines.launch
import androidx.compose.runtime.*

sealed interface AlcoholicCocktailUiState {
    data class Success(val alcoholic: String): AlcoholicCocktailUiState
    object Loading: AlcoholicCocktailUiState
    object Error: AlcoholicCocktailUiState
}

class CocktailViewModel: ViewModel(){

    var alcoholicCocktailUiState: AlcoholicCocktailUiState by mutableStateOf(AlcoholicCocktailUiState.Loading)
    private set

    init{
        getAlcoholicCocktails()
    }

    private fun getAlcoholicCocktails(){
        viewModelScope.launch {
            alcoholicCocktailUiState = try {
                val alcoholic = CocktailApi.retrofitService.getAlcoholic()
                val listResult = alcoholic.drinks
                AlcoholicCocktailUiState.Success(listResult[0].strDrink)
            } catch (e: Exception) {
                AlcoholicCocktailUiState.Error
            }
        }
    }
}