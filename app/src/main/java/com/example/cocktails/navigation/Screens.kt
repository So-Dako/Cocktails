package com.example.cocktails.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Screens: NavKey {
    @Serializable
    data object Home: Screens
    @Serializable
    data class CocktailDetail(val drinkId: String): Screens
    @Serializable
    data class IngredientInfo(val ingredient: String): Screens
}