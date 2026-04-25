package com.example.cocktails.ui.screens.alcoholicCocktail

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktails.network.AlcoholicCocktail
import com.example.cocktails.network.CocktailApiService
import com.example.cocktails.network.OkHttpClientManager
import com.example.cocktails.network.getRetrofit
import kotlinx.coroutines.launch

sealed interface AlcoholicCocktailUiState {
    data class Success(val alcoholicCocktailList: List<AlcoholicCocktail>): AlcoholicCocktailUiState
    object Loading: AlcoholicCocktailUiState
    object Error: AlcoholicCocktailUiState
}

class AlcoholicCocktailViewModel(application: Application): AndroidViewModel(application){

    var alcoholicCocktailUiState: AlcoholicCocktailUiState by mutableStateOf(AlcoholicCocktailUiState.Loading)
    private set

    val context = getApplication<Application>()

    init {
        getAlcoholicCocktails()
    }

    val retrofitService: CocktailApiService by lazy {
        getRetrofit(OkHttpClientManager.getOkHttpClient(context))
            .create(CocktailApiService::class.java)
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
