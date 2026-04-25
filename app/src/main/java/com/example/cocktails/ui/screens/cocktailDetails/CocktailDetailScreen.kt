package com.example.cocktails.ui.screens.cocktailDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cocktails.network.Drink
import com.example.cocktails.ui.screens.ErrorScreen
import com.example.cocktails.ui.screens.LoadingScreen

@Composable
fun CocktailDetailScreen(cocktailDetailUiState: CocktailDetailUiState,
                         cocktailId: String,
                         modifier: Modifier = Modifier){
    val cocktailDetailsViewModel: CocktailDetailsViewModel = viewModel()
    when(cocktailDetailUiState) {
        is CocktailDetailUiState.Loading -> LoadingScreen()
        is CocktailDetailUiState.Success -> ResultScreen(cocktailDetail = cocktailDetailUiState.cocktailDetails)
        is CocktailDetailUiState.Error -> ErrorScreen( onRetry = {
            cocktailDetailsViewModel.getDrinkDetails(cocktailId)
        })
    }
}

@Composable
fun ResultScreen(cocktailDetail: Drink, modifier: Modifier = Modifier){
    Column(){

    }
}