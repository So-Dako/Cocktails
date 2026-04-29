package com.example.cocktails.ui.screens.ingredient

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.cocktails.R
import com.example.cocktails.network.Cocktail
import com.example.cocktails.ui.screens.ErrorScreen
import com.example.cocktails.ui.screens.LoadingScreen
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun IngredientScreen(
    ingredientUiState: IngredientUiState,
    ingredient: String,
    modifier: Modifier = Modifier
) {
    val ingredientViewModel: IngredientViewModel = viewModel()
    when(ingredientUiState) {
        is IngredientUiState.Loading -> LoadingScreen()
        is IngredientUiState.Success -> ResultScreen(
            cocktailList = ingredientUiState.cocktailList,
            ingredient = ingredient
            )
        is IngredientUiState.Error -> ErrorScreen(
            onRetry = { ingredientViewModel.getCocktails(ingredient) }
        )
    }
}

@Composable
fun ResultScreen (
    cocktailList: List<Cocktail>,
    ingredient: String,
    modifier: Modifier = Modifier
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            GlideImage(
                imageModel = { "https://www.thecocktaildb.com/images/ingredients/$ingredient.png" },
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f),
                requestOptions = {
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                },
                failure = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_connection_error),
                            contentDescription = null,
                            modifier = Modifier.size(100.dp)
                        )
                    }
                }
            )
            Text(
                text = ingredient,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .padding(8.dp))
            cocktailList.chunked(2).forEach { rowItems ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    rowItems.forEach { item ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)

                        ) {
                            GlideImage(
                                imageModel = { item.strDrinkThumb },
                                modifier = modifier
                                    .aspectRatio(1f),
                                requestOptions = {
                                    RequestOptions()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                },
                                failure = {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.ic_connection_error),
                                            contentDescription = null,
                                            modifier = Modifier.size(50.dp)
                                        )
                                    }
                                }
                            )
                            Text(
                                text = item.strDrink,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    repeat(2 - rowItems.size) {
                        Box(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}