package com.example.cocktails.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.cocktails.ui.screens.Home
import com.example.cocktails.ui.screens.cocktailDetails.CocktailDetailScreen
import com.example.cocktails.ui.screens.cocktailDetails.CocktailDetailsViewModel
import com.example.cocktails.ui.screens.ingredient.IngredientScreen
import com.example.cocktails.ui.screens.ingredient.IngredientViewModel

@Composable
fun AppNavigation(backStack: SnapshotStateList<Screens>) {
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Screens.Home> { Home( onNavigate = {key -> backStack.add(key)}) }
            entry<Screens.CocktailDetail> { key ->
                val cocktailDetailsViewModel: CocktailDetailsViewModel = viewModel()
                LaunchedEffect(key.drinkId) {
                    cocktailDetailsViewModel.getDrinkDetails(key.drinkId)
                }
                CocktailDetailScreen(
                    cocktailDetailsViewModel.cocktailDetailUiState,
                    key.drinkId,
                    onNavigate = {key -> backStack.add(key)}
                )
            }
            entry<Screens.IngredientInfo> { key ->
                val ingredientViewModel: IngredientViewModel = viewModel()
                LaunchedEffect(key.ingredient) {
                    ingredientViewModel.getCocktails(key.ingredient)
                }
                IngredientScreen(
                    ingredientViewModel.ingredientUiState,
                    ingredient = key.ingredient
                    )
            }
        }
    )
}