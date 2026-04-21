package com.example.cocktails.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktails.network.CocktailApi
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import com.example.cocktails.network.AlcoholicCocktail

sealed interface AlcoholicCocktailUiState {
    data class Success(val alcoholicCocktailList: List<AlcoholicCocktail>): AlcoholicCocktailUiState
    object Loading: AlcoholicCocktailUiState
    object Error: AlcoholicCocktailUiState
}

class AlcoholicCocktailViewModel: ViewModel(){

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
                AlcoholicCocktailUiState.Success(listResult)
            } catch (e: Exception) {
                AlcoholicCocktailUiState.Error
            }
        }
    }
}