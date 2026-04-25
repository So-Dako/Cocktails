package com.example.cocktails.ui.screens.cocktailDetails

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktails.network.CocktailApiService
import com.example.cocktails.network.Drink
import com.example.cocktails.network.OkHttpClientManager
import com.example.cocktails.network.getRetrofit
import kotlinx.coroutines.launch

sealed interface CocktailDetailUiState {
    data class Success(val cocktailDetails: Drink): CocktailDetailUiState
    object Loading: CocktailDetailUiState
    object Error: CocktailDetailUiState

}

class CocktailDetailsViewModel(application: Application): AndroidViewModel(application){

    var cocktailDetailUiState: CocktailDetailUiState by mutableStateOf(CocktailDetailUiState.Loading)
    private set

    val context = getApplication<Application>()


    val retrofitService: CocktailApiService by lazy {
        getRetrofit(OkHttpClientManager.getOkHttpClient(context))
            .create(CocktailApiService::class.java)
    }

    fun getDrinkDetails(id: String){
        viewModelScope.launch() {
            cocktailDetailUiState = try {
                val detail = retrofitService.getDrinkDetail(id)
                CocktailDetailUiState.Success(detail)
            } catch (e: Exception){
                CocktailDetailUiState.Error
            }
        }
    }
}