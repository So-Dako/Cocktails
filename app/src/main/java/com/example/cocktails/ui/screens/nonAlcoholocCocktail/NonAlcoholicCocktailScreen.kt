package com.example.cocktails.ui.screens.nonAlcoholocCocktail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cocktails.network.NonAlcoholicCocktail
import com.example.cocktails.ui.elements.CocktailCard
import com.example.cocktails.ui.screens.ErrorScreen
import com.example.cocktails.ui.screens.LoadingScreen

@Composable
fun NonAlcoholicCocktailsScreen(nonAlcoholicCocktailUiState: NonAlcoholicCocktailUiState, modifier: Modifier = Modifier) {
    val nonAlcoholicCocktailViewModel: NonAlcoholicCocktailViewModel = viewModel()
    when (nonAlcoholicCocktailUiState) {
        is NonAlcoholicCocktailUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is NonAlcoholicCocktailUiState.Success -> ResultScreen(
            nonAlcoholicCocktailUiState.nonAlcoholicCocktailList,
            modifier = modifier.fillMaxSize()
        )
        is NonAlcoholicCocktailUiState.Error -> ErrorScreen(
            modifier = modifier.fillMaxSize(),
            onRetry = {nonAlcoholicCocktailViewModel.getNonAlcoholicCocktails()}
        )
    }
}

@Composable
fun ResultScreen(nonAlcoholicCocktailUiState: List<NonAlcoholicCocktail>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(nonAlcoholicCocktailUiState) { item ->
            CocktailCard(item, modifier = modifier.fillMaxSize())
        }
    }
}