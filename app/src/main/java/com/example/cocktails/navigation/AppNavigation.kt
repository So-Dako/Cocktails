package com.example.cocktails.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
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
        transitionSpec = {
            slideInVertically { it } togetherWith fadeOut()
        },
        popTransitionSpec = {
            fadeIn() togetherWith slideOutVertically { it }
        },
        entryProvider = entryProvider {
            entry<Screens.Home> { Home( onNavigate = {key -> backStack.add(key)}) }
            entry<Screens.CocktailDetail> { key ->
                val cocktailDetailsViewModel: CocktailDetailsViewModel = viewModel(key = key.drinkId)
                LaunchedEffect(key.drinkId) {
                    cocktailDetailsViewModel.getDrinkDetails(key.drinkId)
                }
                CocktailDetailScreen(
                    cocktailDetailsViewModel.cocktailDetailUiState,
                    key.drinkId,
                    onNavigate = {key -> backStack.add(key)},
                    onBack = { backStack.removeLastOrNull() }
                )
            }
            entry<Screens.IngredientInfo> { key ->
                val ingredientViewModel: IngredientViewModel = viewModel(key = key.ingredient)
                LaunchedEffect(key.ingredient) {
                    ingredientViewModel.getCocktails(key.ingredient)
                }
                IngredientScreen(
                    ingredientViewModel.ingredientUiState,
                    onNavigate = {key -> backStack.add(key)},
                    ingredient = key.ingredient,
                    onBack = { backStack.removeLastOrNull() },
                    onRetry = { ingredientViewModel.getCocktails(key.ingredient) }
                    )
            }
        }
    )
}