package com.example.cocktails.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.dp
import com.example.cocktails.R
import com.example.cocktails.network.AlcoholicCocktail

@Composable
fun AlcoholicCocktailsScreen(alcoholicCocktailUiState: AlcoholicCocktailUiState, modifier: Modifier = Modifier) {
    when (alcoholicCocktailUiState) {
        is AlcoholicCocktailUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is AlcoholicCocktailUiState.Success -> ResultScreen(
            alcoholicCocktailUiState.alcoholicCocktailList,
            modifier = modifier.fillMaxSize()
        )
        is AlcoholicCocktailUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ResultScreen(alcoholicCocktailUiState: List<AlcoholicCocktail>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(alcoholicCocktailUiState) { item ->
            Text(text = item.strDrink)
        }
    }
}