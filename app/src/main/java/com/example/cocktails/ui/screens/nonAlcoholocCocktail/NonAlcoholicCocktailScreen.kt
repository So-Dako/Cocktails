package com.example.cocktails.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cocktails.R
import com.example.cocktails.network.NonAlcoholicCocktail

@Composable
fun AlcoholicCocktailsScreen(nonAlcoholicCocktailUiState: NonAlcoholicCocktailUiState, modifier: Modifier = Modifier) {
    when (nonAlcoholicCocktailUiState) {
        is NonAlcoholicCocktailUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is NonAlcoholicCocktailUiState.Success -> ResultScreen(
            nonAlcoholicCocktailUiState.nonAlcoholicCocktailList,
            modifier = modifier.fillMaxSize()
        )
        is NonAlcoholicCocktailUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ResultScreen(nonAlcoholicCocktailUiState: List<NonAlcoholicCocktail>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(nonAlcoholicCocktailUiState) { item ->
            Text(text = item.strDrink)
        }
    }
}