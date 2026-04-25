package com.example.cocktails.ui.screens.alcoholicCocktail

import androidx.compose.foundation.clickable
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
import com.example.cocktails.network.AlcoholicCocktail
import com.example.cocktails.ui.elements.CocktailCard
import com.example.cocktails.ui.screens.ErrorScreen
import com.example.cocktails.ui.screens.LoadingScreen
import com.example.cocktails.ui.screens.cocktailDetails.CocktailDetailsViewModel

@Composable
fun AlcoholicCocktailsScreen(alcoholicCocktailUiState: AlcoholicCocktailUiState,
                             modifier: Modifier = Modifier
) {
    val alcoholicCocktailViewModel: AlcoholicCocktailViewModel = viewModel()
    when (alcoholicCocktailUiState) {
        is AlcoholicCocktailUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is AlcoholicCocktailUiState.Success -> ResultScreen(
            alcoholicCocktailUiState.alcoholicCocktailList,
            modifier = modifier.fillMaxSize()
        )
        is AlcoholicCocktailUiState.Error -> ErrorScreen(
            modifier = modifier.fillMaxSize(),
            onRetry = {alcoholicCocktailViewModel.getAlcoholicCocktails()}
            )
    }
}

@Composable
fun ResultScreen(alcoholicCocktailUiState: List<AlcoholicCocktail>, modifier: Modifier = Modifier) {
    val cocktailDetailsViewModel: CocktailDetailsViewModel = viewModel()
    var cocktailId: String = ""
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
        items(alcoholicCocktailUiState) { item ->
            CocktailCard(
                item,
                modifier = modifier
                    .fillMaxSize()
                    .clickable(
                        enabled = true,
                        onClick = { cocktailId = item.idDrink }
                    )
            )
        }
    }
}