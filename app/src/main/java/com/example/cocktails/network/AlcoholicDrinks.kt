package com.example.cocktails.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DrinksList(
    @SerialName("drinks")
    val drinks: List<AlcoholicDrinks>
)

@Serializable
data class AlcoholicDrinks(
    @SerialName("strDrink")
    val strDrink: String,
    @SerialName("strDrinkThumb")
    val strDrinkThumb: String,
    @SerialName("idDrink")
    val idDrink: String
)
