package com.example.cocktails.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NonAlcoholicCocktailList(
    @SerialName("drinks")
    val drinks: List<NonAlcoholicCocktail>
)

@Serializable
data class NonAlcoholicCocktail(
    @SerialName("strDrink")
    val strDrink: String,
    @SerialName("strDrinkThumb")
    val strDrinkThumb: String,
    @SerialName("idDrink")
    val idDrink: String
)
