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
import com.example.cocktails.navigation.Screens
import com.example.cocktails.network.Cocktail
import com.example.cocktails.ui.elements.CocktailCard
import com.example.cocktails.ui.screens.ErrorScreen
import com.example.cocktails.ui.screens.LoadingScreen

@Composable
fun AlcoholicCocktailsScreen(alcoholicCocktailUiState: AlcoholicCocktailUiState,
                             onRetry: () -> Unit,
                             onNavigate: (Screens) -> Unit,
                             modifier: Modifier = Modifier
) {
    when (alcoholicCocktailUiState) {
        is AlcoholicCocktailUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is AlcoholicCocktailUiState.Success -> ResultScreen(
            alcoholicCocktailUiState.alcoholicCocktailList,
            onNavigate = onNavigate,
            modifier = modifier.fillMaxSize()
        )
        is AlcoholicCocktailUiState.Error -> ErrorScreen(
            modifier = modifier.fillMaxSize(),
            onRetry = onRetry
            )
    }
}

@Composable
fun ResultScreen(
    alcoholicCocktailUiState: List<Cocktail>,
    onNavigate: (Screens) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
        items(alcoholicCocktailUiState) { item ->
            CocktailCard(
                item,
                modifier = modifier
                    .fillMaxSize()
                    .clickable(
                        enabled = true,
                        onClick = { onNavigate(Screens.CocktailDetail(item.idDrink)) }
                    )
            )
        }
    }
}