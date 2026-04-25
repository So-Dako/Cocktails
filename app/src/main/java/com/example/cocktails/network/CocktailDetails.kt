package com.example.cocktails.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Drink(
    @SerialName("drinks")
    val drinks: CocktailDetails
)

@Serializable
data class CocktailDetails(
    @SerialName("idDrink")
    val idDrink: String,
    @SerialName("strDrink")
    val strDrink: String,
    @SerialName("strInstructions")
    val strInstructions: String,
    @SerialName("strDrinkThumb")
    val strDrinkThumb: String,
    @SerialName("strGlass")
    val strGlass: String,
    val ingredients: Ingredients,
    val measures: Measures
)

@Serializable
data class Ingredients(
    val strIngredient1: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strIngredient6: String?,
    val strIngredient7: String?,
    val strIngredient8: String?,
    val strIngredient9: String?,
    val strIngredient10: String?,
    val strIngredient11: String?,
    val strIngredient12: String?
){
    fun getListOfIngredients(): List<String>{
        return listOfNotNull(strIngredient1, strIngredient2, strIngredient3,
            strIngredient4, strIngredient5, strIngredient6, strIngredient7, strIngredient8,
            strIngredient9, strIngredient10, strIngredient11, strIngredient12)
    }
}


@Serializable
data class Measures(
    val strMeasure1: String?,
    val strMeasure2: String?,
    val strMeasure3: String?,
    val strMeasure4: String?,
    val strMeasure5: String?,
    val strMeasure6: String?,
    val strMeasure7: String?,
    val strMeasure8: String?,
    val strMeasure9: String?,
    val strMeasure10: String?,
    val strMeasure11: String?,
    val strMeasure12: String?
){
    fun getListOfMeasures(): List<String>{
        return listOfNotNull(strMeasure1, strMeasure2, strMeasure3,
            strMeasure4, strMeasure5, strMeasure6, strMeasure7, strMeasure8,
            strMeasure9, strMeasure10, strMeasure11, strMeasure12)
    }
}