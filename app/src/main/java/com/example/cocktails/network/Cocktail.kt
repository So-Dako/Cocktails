package com.example.cocktails.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CocktailList(
    @SerialName("drinks")
    val drinks: List<Cocktail>
)

@Serializable
data class Cocktail(
    @SerialName("strDrink")
    val strDrink: String,
    @SerialName("strDrinkThumb")
    val strDrinkThumb: String,
    @SerialName("idDrink")
    val idDrink: String
)