package com.example.cocktails.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlcoholicCocktailList(
    @SerialName("drinks")
    val drinks: List<AlcoholicCocktail>
)

@Serializable
data class AlcoholicCocktail(
    @SerialName("strDrink")
    val strDrink: String,
    @SerialName("strDrinkThumb")
    val strDrinkThumb: String,
    @SerialName("idDrink")
    val idDrink: String
)
